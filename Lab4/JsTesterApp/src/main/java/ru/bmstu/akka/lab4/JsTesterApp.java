package ru.bmstu.akka.lab4;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class JsTesterApp {
    public static ActorRef storeActor;
    public static ActorRef routActor;

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("lab4");

    }
}
