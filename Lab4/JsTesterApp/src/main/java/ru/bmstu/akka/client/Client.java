package ru.bmstu.akka.client;

import akka.actor.ActorSystem;
import akka.stream.Materializer;

public class Client {
    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create();
        final Materializer materializer = ActorMaterializer.create(system);

    }
}
