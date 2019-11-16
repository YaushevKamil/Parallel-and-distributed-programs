package ru.bmstu.akka.lab5;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.japi.Pair;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.asynchttpclient.AsyncHttpClient;
import ru.bmstu.akka.lab5.Actors.CacheActor;
import ru.bmstu.akka.lab5.Messages.GetMessage;

import java.util.Map;

import static ru.bmstu.akka.lab5.LoadTestingApp.HOST;

public class Tester {
    private final ActorMaterializer materializer;
    private final ActorRef cacheActor;
    private final AsyncHttpClient asyncHttpClient;

    private static final String URL_KEY = "testUrl";
    private static final String COUNT_KEY = "count";
    private static final int TIMEOUT_MS = 5000;

    public Tester(ActorSystem system, ActorMaterializer materializer, AsyncHttpClient asyncHttpClient) {
        this.materializer = materializer;
        this.cacheActor = system.actorOf(Props.create(CacheActor.class));
        this.asyncHttpClient = asyncHttpClient;
    }

    public Flow<HttpRequest, HttpResponse, NotUsed> createFlow() {
        return Flow.of(HttpRequest.class)
                .map(req -> {

                })
                .mapAsync(4, pair -> {
                    String url = pair.first();
                    Integer count = pair.second();
                    return Patterns.ask(cacheActor, new GetMessage(url, count), TIMEOUT_MS));
                }
                .map();
    }

    private Pair<String, Integer> parseRequest(HttpRequest request) {
        Map<String, String> query = request.getUri().query().toMap();
        if (query.containsKey(URL_KEY) && query.containsKey(COUNT_KEY)) {
            String url = query.get(URL_KEY);
            Integer count = Integer.parseInt(query.get(COUNT_KEY));
            return new Pair<String, Integer>(url, count);
        } else {
            return new Pair<String, Integer>(HOST, 1);
        }
    }
}
