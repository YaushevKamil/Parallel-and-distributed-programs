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
        String zkAddress = args[0];
        String hostAddress = args[1];
        URI uri = new URI("http://" + hostAddress);
        String host = uri.getHost();
        int port = uri.getPort();

        //http://localhost:8082/?url=http://rambler.ru&count=20
        
        Server server = new Server(host, port, zkAddress);
        server.start();
        System.in.read();
        server.terminate();
    }
}
