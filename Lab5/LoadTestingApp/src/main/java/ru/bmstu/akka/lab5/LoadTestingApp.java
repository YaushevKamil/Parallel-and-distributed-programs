package ru.bmstu.akka.lab5;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

public class LoadTestingApp {
    public static void main(String[] args)  {
        System.out.println("Start!");
        ActorSystem system = ActorSystem.create("routes");
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        LoadTestingApp tester = new LoadTestingApp();

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = tester
                .createRoute(http, system, materializer)
                .flow(system, materializer);
    }
}