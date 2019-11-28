package ru.bmstu.akka.lab6;

import akka.actor.ActorRef;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Coordinator {
    private final int SESSION_TIMEOUT_MS = 3000;
    private final String ROOT_PATH = "/servers";
    private final String NODE_PATH = ROOT_PATH + "/s";

    private final String zooKeeperHost;
    private final ActorRef storeActor;
    private ZooKeeper zoo;

    Coordinator(String zooKeeperHost, ActorRef storeActor) throws IOException {
        this.zooKeeperHost = zooKeeperHost;
        this.storeActor = storeActor;
        this.zoo = new ZooKeeper(zooKeeperHost, SESSION_TIMEOUT_MS, (Watcher) this);

    }

    private void watchNodes() {
        List<String> servers = zoo.getChildren(ROOT_PATH, this);
        List<String> addresses = new ArrayList<>();
        for (String server : servers) {
            byte[] addr = zoo.getData(ROOT_PATH + s, false, null);
            System.out.println("server " + s + " data=" + new String(data));
        }
    }

    void terminate() {
        try {
            zoo.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
