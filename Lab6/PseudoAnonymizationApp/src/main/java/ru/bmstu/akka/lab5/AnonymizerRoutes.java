package ru.bmstu.akka.lab5;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;

public class AnonymizerRoutes extends AllDirectives {
    private static final String URL_ARG_NAME = "url";
    private static final String COUNT_ARG_NAME = "count";

    private final ActorSystem system;
    private final ActorRef storeActor;

    public AnonymizerRoutes(ActorSystem system, ActorRef storeActor) {
        this.system = system;
        this.storeActor = storeActor;
    }

    public Route getRoutes() {
        return route(
                get(() -> {
                    parameter()
                })
        );
    }
}
