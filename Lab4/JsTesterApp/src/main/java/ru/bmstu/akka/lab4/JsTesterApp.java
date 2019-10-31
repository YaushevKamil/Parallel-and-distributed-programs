package ru.bmstu.akka.lab4;

import akka.NotUsed;
import akka.actor.*;
import akka.http.javadsl.Http;
import akka.http.javadsl.IncomingConnection;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.japi.pf.DeciderBuilder;
import akka.routing.RoundRobinPool;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import ru.bmstu.akka.lab4.messages.GetMessage;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.List;
import java.util.regex.Pattern;

import static akka.actor.SupervisorStrategy.*;

public class JsTesterApp extends AllDirectives {
    public static ActorRef storeActor;
    public static ActorRef routeActor;

    private static final int TIMEOUT_MS = 5000;
    private static final int MAX_RETRIES = 10;
    private static SupervisorStrategy strategy =
            new OneForOneStrategy(MAX_RETRIES,
                    Duration.create("1 minute"),
                    DeciderBuilder.
                            match(ArithmeticException.class, e -> resume()).
                            match(NullPointerException.class, e -> restart()).
                            match(IllegalArgumentException.class, e -> stop()).
                            matchAny(o -> escalate()).build());


    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("lab4");
        storeActor = system.actorOf(Props.create(StoreActor.class));
        routeActor = system.actorOf(new RoundRobinPool(5)
                .withSupervisorStrategy(strategy)
                .props(Props.create(), "router"));
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        JsTesterApp tester = new JsTesterApp();

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =
                tester.createRoute(system).flow(system, materializer);

    }

    private Route createRoute(ActorSystem system) {
        return route(
                path("test", () ->
                        post(() -> entity(Jackson.unmarshaller(Tests.class), msg -> {
//                            System.out.println("post()");
                            String packageId = msg.getPackageId();
                            String functionName = msg.getFunctionName();
                            String script = msg.getJsScript();
                            List<Test> tests = msg.getTests();
                            String expectedResult;
                            for (Test test : tests) {
                                routeActor.tell(new JsFunction(packageId,
                                                               functionName,
                                                               script,
                                                               test.getParams(),
                                                               test.getExpectedResult()),
                                        ActorRef.noSender());
                            }
                            return complete("\nTest started\n");
                        }))),
                path("put", () ->
                        get(() -> parameter("packageId", (packageId) -> {
                            System.out.println("get()");
                            Future<Object> output = Pattern.ask(storeActor, new GetMessage(packageId), TIMEOUT_MS);
                            return completeOKWithFuture(output, Jackson.marshaller());
                                        })))
        );
    }
}
