package ru.bmstu.akka.lab5;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Anonymizer {
    private ActorRef storeActor;

    public Anonymizer(ActorSystem system, String zooKeeperHost, String host, String port) {
        storeActor = system.actorOf(Props.create())

    }
}
