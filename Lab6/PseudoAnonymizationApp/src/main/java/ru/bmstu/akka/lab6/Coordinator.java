package ru.bmstu.akka.lab6;

import akka.actor.ActorRef;

class Coordinator {
    private final String zooKeeperHost;
    private final ActorRef storeActor;

    Coordinator(String zooKeeperHost, ActorRef storeActor) {
        this.zooKeeperHost = zooKeeperHost;
        this.storeActor = storeActor;
    }
}
