package ru.bmstu.akka.lab6.Anonymizer;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.*;
import akka.http.javadsl.server.Route;
import akka.japi.Pair;
import akka.pattern.Patterns;
import ru.bmstu.akka.lab6.Messages.GetMessage;
import ru.bmstu.akka.lab6.Messages.ResponseMessage;
import scala.compat.java8.FutureConverters;

import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;

public class ServerRoutes {
    private final static String SCHEME = "http://";
    private static final String URL_ARG_NAME = "url";
    private static final String COUNT_ARG_NAME = "count";
    private static final int TIMEOUT_MS = 5000;

    private static int strToInt(String numString) {
        return numString.length() > 0 ?
                Integer.parseInt(numString) :
                0;
    }

    public static Uri getUri(String address) {
        return Uri.create(SCHEME + address);
    }

    static Route getRoutes(ActorSystem system, ActorRef storeActor) {
        return route(
                get(() ->
                    parameter(URL_ARG_NAME, url ->
                        parameter(COUNT_ARG_NAME, countString -> {
                            int count = strToInt(countString);
                            return count == 0 ?
                                    completeWithFuture(makeRequest(system, url)) :
                                    completeWithFuture(redirectRequest(system, storeActor, url, count));
                        })
                    )
                )
        );
    }

    private static CompletionStage<HttpResponse> makeRequest(ActorSystem system, String url) {
        System.out.println(url);
        return Http.get(system).singleRequest(HttpRequest.create(url));
    }

    private static CompletionStage<HttpResponse> redirectRequest(ActorSystem system, ActorRef storeActor, String url, int count) {
        return FutureConverters.toJava(
                Patterns.ask(storeActor, new GetMessage(), TIMEOUT_MS)
            )
                .thenApply(o -> (ResponseMessage)o)
                .thenCompose(msg -> makeRequest(
                        system,
                        getUri(msg.getAddress())
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