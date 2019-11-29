package ru.bmstu.akka.lab6;

import org.apache.zookeeper.KeeperException;
import ru.bmstu.akka.lab6.Anonymizer.Server;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PseudoAnonymizationApp {
    public static void main(String[] args) throws InterruptedException, IOException, KeeperException, URISyntaxException {
        if (args.length < 2) {
            System.out.println("Usage: PseudoAnonymizationApp <zkAddr> <anonAddr>");
            System.exit(-1);
        }
        String connectString = args[0];
        String address = args[1];

        URI uri = new URI("http://" + address);
        String host = uri.getHost();
        int port = uri.getPort();

        Server server = new Server(host, port, connectString);
        //http://localhost:8082/?url=http://rambler.ru&count=20
        System.in.read();

        server.terminate();
    }
}
