package ru.bmstu.akka.lab5;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.asynchttpclient.AsyncHttpClient;

public class Tester {
    private final ActorMaterializer materializer;
    private final ActorRef cacheActor

    public Tester(ActorSystem system, ActorMaterializer materializer, AsyncHttpClient asyncHttpClient) {
        this.materializer = materializer;

    }

    public Flow<HttpRequest, HttpResponse, NotUsed> createFlow() {
    }
}
