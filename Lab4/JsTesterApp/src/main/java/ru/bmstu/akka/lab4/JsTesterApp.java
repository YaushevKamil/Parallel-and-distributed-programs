package ru.bmstu.akka.lab4;

import akka.actor.*;
import akka.http.javadsl.Http;
import akka.http.javadsl.server.AllDirectives;
import akka.japi.pf.DeciderBuilder;
import akka.routing.RoundRobinPool;
import akka.stream.ActorMaterializer;
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
    }
}
