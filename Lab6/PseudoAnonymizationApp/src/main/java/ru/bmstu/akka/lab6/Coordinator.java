package ru.bmstu.akka.lab6;

import akka.actor.ActorRef;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import ru.bmstu.akka.lab6.Messages.StoreMessage;

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
            byte[] address = zoo.getData(ROOT_PATH + '/' + server, false, null);
            addresses.add(new String(address));
        }
        storeActor.tell(new StoreMessage(addresses.toArray(String[])));
    }

    void terminate() {
        try {
            zoo.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
