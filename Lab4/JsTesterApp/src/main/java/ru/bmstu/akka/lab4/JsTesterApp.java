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
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.*;

public class JsTesterApp extends AllDirectives {
    public static ActorRef storeActor;
    public static ActorRef routeActor;

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
                            System.out.println("post()");
                            testPackageActor.tell(msg, ActorRef.noSender());
                            return complete("Test started!");
                        })))),
                path("put", () ->
                        get(() ->
                                parameter("key", (key) ->
                                        parameter("value", (value) ->
                                        {
                                            storeActor.tell(new StoreActor.StoreMessage(key, value), ActorRef.noSender());
                                            return complete("value saved to store ! key=" + key + " value=" + value);
                                        })))
        );
    }
}
