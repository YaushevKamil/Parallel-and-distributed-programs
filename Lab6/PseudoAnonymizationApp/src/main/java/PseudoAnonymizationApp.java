import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;

public class PseudoAnonymizationApp {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: PseudoAnonymizationApp <ZooKeeperAddr> <host> <port_1> ( ... <port_n> )");


            ActorSystem system = ActorSystem.create("anonymizer");

            final Http http = Http.get(system);
            final ActorMaterializer materializer = ActorMaterializer.create(system);

            final Tester tester = new Tester(system, materializer, asyncHttpClient);

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
                        try {
                            asyncHttpClient.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
        }
    }
}
