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

    private static int strToInt(String numString) {
        return numString.length() > 0 ?
                Integer.parseInt(numString) :
                0;
    }

    public Route getRoutes() {
        return route(
                get(() -> {
                    parameter(URL_ARG_NAME, url -> {
                        parameter(COUNT_ARG_NAME, countString -> {
                            int count = strToInt(countString);
                            return count == 0 ?
                                    CompleteW
                        })
                    })
                })
        );
    }
}
