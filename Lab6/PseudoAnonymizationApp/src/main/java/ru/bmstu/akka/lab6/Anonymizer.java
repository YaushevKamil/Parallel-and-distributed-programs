package ru.bmstu.akka.lab6;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.server.Route;
import ru.bmstu.akka.lab6.Actors.StoreActor;

public class Anonymizer {
    private final AnonymizerRoutes routes;

    public Anonymizer(ActorSystem system, String zooKeeperHost, String host, String port) {
        ActorRef storeActor = system.actorOf(Props.create(StoreActor.class), "HostStorage");

        routes = new AnonymizerRoutes(system, storeActor);
    }

    public Route createRoutes() {
        return routes.getRoutes();
    }
}
