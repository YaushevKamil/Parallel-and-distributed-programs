package ru.bmstu.akka.lab5;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;

public class LoadTestingApp {
    public static void main(String[] args)  {
        System.out.println("Start!");
        ActorSystem system = ActorSystem.create("lab5");
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
    }
}