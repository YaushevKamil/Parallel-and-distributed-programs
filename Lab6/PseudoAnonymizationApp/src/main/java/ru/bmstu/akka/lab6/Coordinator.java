package ru.bmstu.akka.lab6;

import akka.actor.ActorRef;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

class Coordinator {
    private final int SESSION_TIMEOUT_MS = 3000;

    private final String zooKeeperHost;
    private final ActorRef storeActor;
    private final ZooKeeper zoo;

    Coordinator(String zooKeeperHost, ActorRef storeActor) {
        this.zooKeeperHost = zooKeeperHost;
        this.storeActor = storeActor;
        createZoo();
        //this.zoo = f();
    }

    private void createZoo() throws IOException {
        Watcher watcher;
        this.zoo = new ZooKeeper(zooKeeperHost, SESSION_TIMEOUT_MS, this);
    }


    void terminate() {
        try {
            zoo.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
