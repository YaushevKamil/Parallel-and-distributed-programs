package ru.bmstu.akka.lab4;

import akka.NotUsed;
import akka.actor.*;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.japi.pf.DeciderBuilder;
import akka.pattern.Patterns;
import akka.routing.RoundRobinPool;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static akka.actor.SupervisorStrategy.*;

import ru.bmstu.akka.lab4.messages.GetMessage;

public class JsTesterApp extends AllDirectives {
    private static final String HOST = "http://localhost";
    private static final int PORT = 8080;
    private static final int TIMEOUT_MS = 5000;
    private static final int MAX_RETRIES = 10;

    public static void main(String[] args) throws IOException {
        ActorSystem system = ActorSystem.create("lab4");
        ActorRef storeActor = system.actorOf(Props.create(StoreActor.class));
        ActorRef routeActor = system.actorOf(new RoundRobinPool(5)
//                .withSupervisorStrategy(
//                        new OneForOneStrategy(
//                                MAX_RETRIES,
//                                Duration.create("1 minute"),
//                                DeciderBuilder
//                                        .match(ArithmeticException.class, e -> resume())
//                                        .match(NullPointerException.class, e -> restart())
//                                        .match(IllegalArgumentException.class, e -> stop())
//                                        .matchAny(o -> escalate()).build()
//                        )
//                )
                .props(Props.create(PerformActor.class, storeActor)), "router");
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        JsTesterApp tester = new JsTesterApp();

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = tester
                .createRoute(storeActor)
                .flow(system, materializer);

        final CompletionStage<ServerBinding> binding = http
                .bindAndHandle(
                        routeFlow,
                        ConnectHttp.toHost(HOST, PORT),
                        materializer
                );
        System.out.println("Server online at " + HOST + ':' + PORT + "/\nPress RETURN to stop...");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }

    private Route createRoute(ActorRef storeActor, ActorRef routerActor) {
        return route(
                path("test", () ->
                        post(() -> entity(Jackson.unmarshaller(Tests.class), m -> {
                            System.out.println("post()");
                            String packageId = m.getPackageId();
                            String functionName = m.getFunctionName();
                            String script = m.getJsScript();
                            List<Test> tests = m.getTests();
                            for (Test test : tests) {
                                routeActor.tell(
                                    new JsFunction(
                                        packageId,
                                        functionName,
                                        script,
                                        test.getParams(),
                                        test.getExpectedResult()
                                    ),
                                    ActorRef.noSender()
                                );
                            }
                            return complete("\nTESTED\n");
                        }))),
                path("response", () ->
                        get(() -> parameter("packageId", (packageId) -> {
                            System.out.println("get()");
                            Future<Object> output = Patterns.ask(
                                    storeActor,
                                    new GetMessage(packageId),
                                    TIMEOUT_MS
                                );
                            return completeOKWithFuture(output, Jackson.marshaller());
                        }))));
    }
}
