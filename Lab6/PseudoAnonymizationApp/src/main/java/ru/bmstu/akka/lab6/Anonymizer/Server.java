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
    ActorRef storeActor;
    private final Coordinator coordinator;

    public Server(String host, int port, String connectString) throws InterruptedException, IOException, KeeperException {
        this.host = host;
        this.port = port;
        this.system = ActorSystem.create("anonymizer");
        this.storeActor = system.actorOf(Props.create(StoreActor.class), "HostStorage");
        this.coordinator = new Coordinator(connectString, storeActor, host+':'+port);
    }

    public void start() {
        createHandler();
        System.out.println("Server online at " + (host+':'+port));
    }

    private void createHandler() {
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = new ServerRoutes(system, storeActor)
                .getRoutes()
                .flow(system, materializer);
        this.binding = Http.get(system).bindAndHandle(
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
                    coordinator.terminate();
                });
    }
}
