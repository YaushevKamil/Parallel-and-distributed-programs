package ru.bmstu.akka.client;

import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;

import java.io.File;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create();
        final Materializer materializer = ActorMaterializer.create(system);

        String json = "";
        json = new Scanner(new File())
    }
}
