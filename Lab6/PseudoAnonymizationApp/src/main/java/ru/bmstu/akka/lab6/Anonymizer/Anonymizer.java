package ru.bmstu.akka.lab6.Anonymizer;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.model.Uri;
import akka.http.javadsl.server.Route;
import org.apache.zookeeper.KeeperException;
import ru.bmstu.akka.lab6.Actors.StoreActor;

import java.io.IOException;

public class Anonymizer {
    private final AnonymizerRoutes routes;
    private final Coordinator coordinator;

    public Anonymizer(ActorSystem system, String zooKeeperHost, String host, int port) throws IOException, KeeperException, InterruptedException {
        ActorRef storeActor = system.actorOf(Props.create(StoreActor.class), "HostStorage");
        String address = host + ':' + port;
        coordinator = new Coordinator(zooKeeperHost, storeActor, address);
        routes = new AnonymizerRoutes(system, storeActor);
    }

    public Route createRoutes() {
        return routes.getRoutes();
    }

    public void terminate() {
        coordinator.terminate();
    }
}
