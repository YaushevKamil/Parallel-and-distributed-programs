package ru.bmstu.akka.client;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.MediaTypes;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class Client {
    public static void main(String[] args) throws FileNotFoundException, ExecutionException, InterruptedException {
        final ActorSystem system = ActorSystem.create();
        final Materializer materializer = ActorMaterializer.create(system);

        String json = "";
        json = new Scanner(new File("tests.json")).useDelimiter("\\Z").next();

        final CompletionStage<HttpResponse> postFuture =
                Http.get(system)
                .singleRequest(HttpRequest.POST("http://localhost:8080/tests")
                        .withEntity(MediaTypes.APPLICATION_JSON.toContentType(), json), materializer);
        System.out.println(postFuture.toCompletableFuture().get());

        final CompletionStage<HttpResponse> responseFuture =
                Http.get(system)
                        .singleRequest(HttpRequest.GET("http://localhost:8080/response?packageId=11"), materializer);
        System.out.println(responseFuture.toCompletableFuture().get());
    }
}
