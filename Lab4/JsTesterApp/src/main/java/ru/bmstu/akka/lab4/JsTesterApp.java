package ru.bmstu.akka.lab4;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class JsTesterApp {
    public static ActorRef storeActor;
    public static ActorRef routeActor;

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("lab4");
        storeActor = system.actorOf(Props.create(StoreActor.class));
        routeActor = 
    }
}
