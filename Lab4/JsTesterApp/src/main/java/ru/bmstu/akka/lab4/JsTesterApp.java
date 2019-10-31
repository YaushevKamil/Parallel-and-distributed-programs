package ru.bmstu.akka.lab4;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.Http;
import akka.routing.RoundRobinPool;
import akka.stream.ActorMaterializer;

public class JsTesterApp {
    public static ActorRef storeActor;
    public static ActorRef routeActor;

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("lab4");
        storeActor = system.actorOf(Props.create(StoreActor.class));
        routeActor = system.actorOf(new RoundRobinPool(5)
                .withSupervisorStrategy(strategy)
                .props(Props.create(), "router"));
        final Http http = Http.get(system);
        final ActorMaterializer
    }
}
