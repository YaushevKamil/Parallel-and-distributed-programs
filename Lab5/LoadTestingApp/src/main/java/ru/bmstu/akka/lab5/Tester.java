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
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import org.asynchttpclient.AsyncHttpClient;
import ru.bmstu.akka.lab5.Actors.CacheActor;
import ru.bmstu.akka.lab5.Messages.GetMessage;
import ru.bmstu.akka.lab5.Messages.ResponseMessage;
import ru.bmstu.akka.lab5.Messages.StoreMessage;
import scala.compat.java8.FutureConverters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.regex.Pattern;

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
                .map(this::parseRequest)
                .mapAsync(4, this::processTest)
                .map(this::completeRequest);
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

    private CompletionStage<StoreMessage> processTest(Pair<String, Integer> test) {
        String url = test.first();
        Integer count = test.second();
        return FutureConverters.toJava(
                Patterns.ask(cacheActor, new GetMessage(url, count), TIMEOUT_MS)
                ).thenApply(o -> (ResponseMessage)o)
                .thenCompose(resp -> {
                    Optional<StoreMessage> messageOptional = resp.getResult();
                    return messageOptional.isPresent() ?
                            CompletableFuture.completedFuture(messageOptional.get()) :
                            performTest(test);
                });
    }

    private CompletionStage<StoreMessage> performTest(Pair<String, Integer> test) {
        final Sink<Pair<String, Integer>, CompletionStage<Long>> testSink = createSink(test);
        String url = test.first();
        Integer count = test.second();
        return Source.from(Collections.singletonList(test))
                .toMat(testSink, Keep.right()).run(materializer)
                .thenApply(sum -> new StoreMessage(new GetMessage(url, count), sum/count));
    }

    private Sink<Pair<String, Integer>, CompletionStage<Long>> createSink(Pair<String, Integer> test) {
        return Flow.<Pair<String, Integer>>create()
                .mapConcat(p -> Collections.nCopies(p.second(), p.first()))
                .mapAsync(4, this::getResponseTime)
                .toMat(Sink.fold(0L, Long::sum), Keep.right());
    }

    private CompletionStage<Long> getResponseTime(String url) {
        Long startTime = System.currentTimeMillis();
        return asyncHttpClient
                .prepareGet(url)
                .execute()
                .toCompletableFuture()
                .thenCompose(resp -> {
                    Long currentTime = System.currentTimeMillis();
                    return CompletableFuture.completedFuture(currentTime-startTime);
                });
    }

    private HttpResponse completeRequest(StoreMessage result) {
        System.out.println(result);
        cacheActor.tell(result, ActorRef.noSender());
        return HttpResponse
                .create()
                .withStatus(200)
                .withEntity("Average response time" + result.getDelay() + " ms");
    }
}
