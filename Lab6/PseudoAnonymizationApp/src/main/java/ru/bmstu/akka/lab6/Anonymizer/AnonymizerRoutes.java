package ru.bmstu.akka.lab6.Anonymizer;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.Query;
import akka.http.javadsl.model.Uri;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.japi.Pair;
import akka.pattern.Patterns;
import ru.bmstu.akka.lab6.Messages.GetMessage;
import ru.bmstu.akka.lab6.Messages.ResponseMessage;
import scala.compat.java8.FutureConverters;

import java.util.concurrent.CompletionStage;

class AnonymizerRoutes extends AllDirectives {
    private static final String URL_ARG_NAME = "url";
    private static final String COUNT_ARG_NAME = "count";
    private static final int TIMEOUT_MS = 5000;

    private final ActorSystem system;
    private final ActorRef storeActor;

    AnonymizerRoutes(ActorSystem system, ActorRef storeActor) {
        this.system = system;
        this.storeActor = storeActor;
    }

    private static int strToInt(String numString) {
        return numString.length() > 0 ?
                Integer.parseInt(numString) :
                0;
    }

    Route getRoutes() {
        return route(
                get(() ->
                    parameter(URL_ARG_NAME, url ->
                        parameter(COUNT_ARG_NAME, countString -> {
                            int count = strToInt(countString);
                            return count == 0 ?
                                    completeWithFuture(makeRequest(url)) :
                                    completeWithFuture(redirectRequest(url, count));
                        })
                    )
                )
        );
    }

    private CompletionStage<HttpResponse> makeRequest(String url) {
        return Http.get(system).singleRequest(HttpRequest.create(url));
    }

    private CompletionStage<HttpResponse> redirectRequest(String url, int count) {
        return FutureConverters.toJava(
                Patterns.ask(storeActor, new GetMessage(), TIMEOUT_MS)
            )
                .thenApply(o -> (ResponseMessage)o)
                .thenCompose(msg -> makeRequest(
                        Uri.create(msg.getAddress())
                                .query(
                                        Query.create(
                                                Pair.create(URL_ARG_NAME, url),
                                                Pair.create(COUNT_ARG_NAME, Integer.toString(count-1))
                                        )
                                )
                                .toString()
                    )
            );
    }
}
