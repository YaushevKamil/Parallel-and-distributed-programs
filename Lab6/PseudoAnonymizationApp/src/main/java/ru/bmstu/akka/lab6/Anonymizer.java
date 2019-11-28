package ru.bmstu.akka.lab6;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.model.Uri;
import akka.http.javadsl.server.Route;
import ru.bmstu.akka.lab6.Actors.StoreActor;

class Anonymizer {
    private final AnonymizerRoutes routes;
    private final Coordinator coordinator;

    Anonymizer(ActorSystem system, String zooKeeperHost, String host, int port) {
        ActorRef storeActor = system.actorOf(Props.create(StoreActor.class), "HostStorage");
        String 
        coordinator = new Coordinator(zooKeeperHost, storeActor, );
        System.out.println(Uri.create("").host(host).port(port).toString());
        routes = new AnonymizerRoutes(system, storeActor);
    }

    Route createRoutes() {
        return routes.getRoutes();
    }

    void terminate() {
        coordinator.terminate();
    }
}
