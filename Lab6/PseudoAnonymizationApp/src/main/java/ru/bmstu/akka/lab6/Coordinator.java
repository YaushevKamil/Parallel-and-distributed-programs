package ru.bmstu.akka.lab6;

import akka.actor.ActorRef;
import org.apache.zookeeper.*;
import ru.bmstu.akka.lab6.Messages.StoreMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

class Coordinator {
    private final int SESSION_TIMEOUT_MS = 3000;
    private final String ROOT_PATH = "/servers";
    private final String NODE_PATH = ROOT_PATH + "/s";

    private final String zkAddress;
    private final ActorRef storeActor;
    private ZooKeeper zoo;

    Coordinator(String zkAddress, ActorRef storeActor, String address) throws IOException, KeeperException, InterruptedException {

        this.storeActor = storeActor;
        tryConnect(zooKeeperHost);
        createNode(address);
    }

    private void tryConnect(String address) throws IOException {
        this.zoo = connect(address);
        watchNodes();
    }

    private ZooKeeper connect(String address) throws IOException {
        return new ZooKeeper(address, SESSION_TIMEOUT_MS, watchedEvent ->
                watchConnections(watchedEvent, address));
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

    private byte[] getNodeData(String server) {
        try {
            return zoo.getData(ROOT_PATH + '/' + server, false, null);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private void watchConnections(WatchedEvent watchedEvent, String address) {
        if (watchedEvent.getState() == Watcher.Event.KeeperState.Expired ||
                watchedEvent.getState() == Watcher.Event.KeeperState.Disconnected) {
            try {
                tryConnect(address);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void watchChildren(WatchedEvent watchedEvent) {
        if (watchedEvent.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
            watchNodes();
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
