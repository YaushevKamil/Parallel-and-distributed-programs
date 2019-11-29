package ru.bmstu.akka.lab6.Anonymizer;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.apache.zookeeper.KeeperException;
import ru.bmstu.akka.lab6.Actors.StoreActor;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class Server {
    private final String host;
    private final int port;
    private CompletionStage<ServerBinding> binding;
    private final ActorSystem system;
    private final AnonymizerRoutes routes;
    private final Coordinator coordinator;

    public Server(String host, int port, String connectString) throws InterruptedException, IOException, KeeperException {
        String address = host+':'+port;
        this.host = host;
        this.port = port;
        this.system = ActorSystem.create("anonymizer");
        ActorRef storeActor = system.actorOf(Props.create(StoreActor.class), "HostStorage");
        coordinator = new Coordinator(connectString, storeActor, host);
        routes = new AnonymizerRoutes(system, storeActor);
        createHandler();
        System.out.println("Server online at " + address);
    }

    private void createHandler() {
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = routes.getRoutes().flow(system, materializer);
        binding = Http.get(system).bindAndHandle(
                routeFlow,
                ConnectHttp.toHost(host, port),
                materializer
        );
    }

    public void terminate() {
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> {
                    system.terminate();
                    anonymizer.terminate();
                });
    }
}
