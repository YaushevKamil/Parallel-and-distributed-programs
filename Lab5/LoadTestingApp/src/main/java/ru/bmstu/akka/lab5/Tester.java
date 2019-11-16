package ru.bmstu.akka.lab5;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.asynchttpclient.AsyncHttpClient;
import ru.bmstu.akka.lab5.Actors.CacheActor;

public class Tester {
    private final ActorMaterializer materializer;
    private final ActorRef cacheActor;
    private final AsyncHttpClient asyncHttpClient;

    public Tester(ActorSystem system, ActorMaterializer materializer, AsyncHttpClient asyncHttpClient) {
        this.materializer = materializer;
        this.cacheActor = system.actorOf(Props.create(CacheActor.class));
        this.asyncHttpClient = asyncHttpClient;
    }

    public Flow<HttpRequest, HttpResponse, NotUsed> createFlow() {
        return Flow.of (HttpRequest)
    }
}
