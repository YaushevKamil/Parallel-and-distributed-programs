package ru.bmstu.akka.lab5;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Futures;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.IncomingConnection;
import akka.http.javadsl.ServerBinding;
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
import org.asynchttpclient.Response;

import ru.bmstu.akka.lab5.Actors.CacheActor;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;


import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static akka.dispatch.Futures.future;
import static org.asynchttpclient.Dsl.asyncHttpClient;

public class LoadTestingApp {
    private static final String HOST = "http://localhost";
    private static final int PORT = 8080;
    private static final int TIMEOUT_MS = 5000;

    private static final String URL_KEY = "testUrl";
    private static final String COUNT_KEY = "count";

    private static final int COUNT_ZERO = 0;

    public static void main(String[] args) throws IOException {
        ActorSystem system = ActorSystem.create("routes");
        ActorRef cacheActor = system.actorOf(Props.create(CacheActor.class));

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        LoadTestingApp tester = new LoadTestingApp();

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = createFlow(http, system, materializer, cacheActor);

        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost(HOST, PORT),
                materializer
        );

        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }

    private static Sink<Pair<String, Integer>, CompletionStage<Long>> createFlowOnFly() {
        return Flow
                .<Pair<String, Integer>>create()
                .mapConcat(p -> new ArrayList<Pair<String, Integer>>(Collections.nCopies(p.second(), p)))
                .mapAsync(4, pair -> {
                    Long startTime = System.currentTimeMillis();
                    String url = pair.first();
                    return asyncHttpClient()
                            .prepareGet(url)
                            .execute()
                            .toCompletableFuture()
                            //.thenApply(Response::getResponseBody)
                            //.thenAccept(System.out::println)
                            .thenCompose(r -> {
                                Response resp = (Response)r;
                                Long currTime = System.currentTimeMillis();
                                return Futures.successful(currTime - startTime);
                            });
                })
                .toMat(Sink.fold(0L, Long::sum), Keep.right());
    }

    private static Flow<HttpRequest,
                        HttpResponse,
                        NotUsed> createFlow(
                                Http http,
                                ActorSystem system,
                                ActorMaterializer materializer,
                                ActorRef cacheActor
    ) {
        return Flow.of(HttpRequest.class)
                .map(req -> {
                    Map<String, String> params = req.getUri().query().toMap();
                    if (!params.containsKey(URL_KEY) ||
                        !params.containsKey(COUNT_KEY)) {
                        System.out.println(params.toString());
                        return new Pair<String, Integer>(HOST, COUNT_ZERO);
                    }
                    String url = params.get(URL_KEY);
                    Integer count = Integer.parseInt(params.get(COUNT_KEY));
                    return new Pair<String, Integer>(url, count);
                })
                .mapAsync(4, pair -> {
                    String url = pair.first();
                    Integer count = pair.second();
                    Future<Object> actorResponse = Patterns.ask(
                            cacheActor,
                            new GetMessage(url, count),
                            TIMEOUT_MS
                    );
                    CompletionStage<Object> stage = FutureConverters.toJava(actorResponse);
                    return stage.thenCompose(r -> {
                        ResponseMessage msg = (ResponseMessage)r;
                        Long delay = msg.getDelay();
//                        СompletionStage<Long>
                        if (msg.getDelay() > 0) {
                            return CompletableFuture.completedFuture(delay);
                        } else {
                            Sink<Pair<String, Integer>, CompletionStage<Long>> testSink = createFlowOnFly();
                            return Source.from(Collections.singletonList(pair))
                                    .toMat(testSink, Keep.right()).run(materializer)
                                    .thenApply(sum -> sum/(float)((count == 0) ? 1 : count));
                        }
                    });
                    //
                })
                .map(res -> {
                    System.out.println(res);
                    cacheActor.tell(new CacheMessage(res.floatValue()), ActorRef.noSender());

                    String averageTime = new DecimalFormat("#0.00").format(res);
                    return HttpResponse
                            .create()
                            .withStatus(200)
                            .withEntity("Average response time" + averageTime + " ms");
                });
    }
}