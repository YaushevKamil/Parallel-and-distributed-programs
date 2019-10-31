package ru.bmstu.akka.client;

import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.CompletionStage;

public class Client {
    public static void main(String[] args) throws FileNotFoundException {
        final ActorSystem system = ActorSystem.create();
        final Materializer materializer = ActorMaterializer.create(system);

        String json = "";
        json = new Scanner(new File("tests.json")).useDelimiter("\\Z").next();

        final CompletionStage
    }
}
