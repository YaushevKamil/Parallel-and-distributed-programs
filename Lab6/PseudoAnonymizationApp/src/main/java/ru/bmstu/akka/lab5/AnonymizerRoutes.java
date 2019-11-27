package ru.bmstu.akka.lab5;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class AnonymizerRoutes {
    private final ActorSystem system;
    private final ActorRef storeActor;

    public AnonymizerRoutes(ActorSystem system, ActorRef storeActor) {
        this.system = system;
        this.storeActor = storeActor;
    }

    public Rou
}
