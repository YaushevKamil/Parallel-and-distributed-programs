package ru.bmstu.akka.lab6;

import akka.actor.ActorRef;
import org.apache.zookeeper.ZooKeeper;

class Coordinator {
    private final String zooKeeperHost;
    private final ActorRef storeActor;
    private final ZooKeeper zoo;

    Coordinator(String zooKeeperHost, ActorRef storeActor) {
        this.zooKeeperHost = zooKeeperHost;
        this.storeActor = storeActor;
        createZoo();
        //this.zoo = f();
    }

    private void createZoo() {
        this.zoo = 
    }


    void terminate() {
        try {
            zoo.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
