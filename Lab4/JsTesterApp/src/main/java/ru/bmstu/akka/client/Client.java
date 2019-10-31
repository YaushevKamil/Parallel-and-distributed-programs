package ru.bmstu.akka.client;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
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

        final CompletionStage<HttpResponse> postFuture =
                Http.get(system)
                .singleRequest(HttpRequest.POST("http://localhost"))
    }
}
