import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class PseudoAnonymizationApp {
    private static final String HOST = "http://localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("Usage: PseudoAnonymizationApp <host> <port_0> (<port_1> ... <port_n>)");
            System.exit(-1);
        }
        String host = args[0];
        String connectString;

        ActorSystem system = ActorSystem.create("anonymizer");

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        final Anonymizer server = new Anonymizer(system, materializer, asyncHttpClient);

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = tester.createFlow();
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost(HOST, PORT),
                materializer
        );

            //http://localhost:8080/?testUrl=http://rambler.ru&count=20

            System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
            System.in.read();
            binding
                    .thenCompose(ServerBinding::unbind)
                    .thenAccept(unbound -> {
                        system.terminate();
                    });
        }
        }
    }
}
