package ru.bmstu.akka.lab6.Anonymizer;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.server.Route;
import org.apache.zookeeper.KeeperException;
import ru.bmstu.akka.lab6.Actors.StoreActor;

import java.io.IOException;

class Anonymizer {
    private final AnonymizerRoutes routes;
    private final Coordinator coordinator;

    Anonymizer(ActorSystem system, String connectString, String host) throws IOException, KeeperException, InterruptedException {
        ActorRef storeActor = system.actorOf(Props.create(StoreActor.class), "HostStorage");
        coordinator = new Coordinator(connectString, storeActor, host);
        routes = new AnonymizerRoutes(system, storeActor);
    }

    Route createRoutes() {
        return routes.getRoutes();
    }

    void terminate() {
        coordinator.terminate();
    }
}
