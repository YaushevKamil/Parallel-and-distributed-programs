package ru.bmstu.akka.lab6;

import akka.actor.ActorRef;
import org.apache.zookeeper.*;
import ru.bmstu.akka.lab6.Messages.StoreMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

class Coordinator {
    private final int SESSION_TIMEOUT_MS = 3000;
    private final String ROOT_PATH = "/servers";
    private final String NODE_PATH = ROOT_PATH + "/s";

    private final String zooKeeperHost;
    private final ActorRef storeActor;
    private ZooKeeper zoo;

    Coordinator(String zooKeeperHost, ActorRef storeActor, String address) throws IOException {
        this.zooKeeperHost = zooKeeperHost;
        this.storeActor = storeActor;
        this.zoo = new ZooKeeper(zooKeeperHost, SESSION_TIMEOUT_MS, (Watcher) this);
        createNode();
    }

    private void createNode(String address) throws KeeperException, InterruptedException {
        zoo.create(
                NODE_PATH,
                address.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL
        );
    }

    private void watchNodes() {
        String[] addresses = Objects.requireNonNull(getChildren())
                .stream()
                .map(Optional::ofNullable)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::getNodeData)
                .map(String::new)
                .toArray(String[]::new);
        storeActor.tell(new StoreMessage(addresses), ActorRef.noSender());
    }

    private List<String> getChildren() {
        try {
            return zoo.getChildren(ROOT_PATH, this::watchChildren);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void watchChildren(WatchedEvent watchedEvent) {
        if (watchedEvent.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
            watchNodes();
        }
    }

    private byte[] getNodeData(String server) {
        try {
            return zoo.getData(ROOT_PATH + '/' + server, false, null);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    void terminate() {
        try {
            zoo.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
