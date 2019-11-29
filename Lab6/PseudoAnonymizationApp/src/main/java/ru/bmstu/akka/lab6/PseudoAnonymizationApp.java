package ru.bmstu.akka.lab6;

import org.apache.zookeeper.KeeperException;
import ru.bmstu.akka.lab6.Anonymizer.Server;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PseudoAnonymizationApp {
    private static final String SCHEME = "http://";
    public static void main(String[] args) throws InterruptedException, IOException, KeeperException, URISyntaxException {
        if (args.length != 2) {
            System.out.println("Usage: PseudoAnonymizationApp <zkAddr> <anonAddr>");
            System.exit(-1);
        }
        String zkAddress = args[0];
        String hostAddress = args[1];
        URI hostUri = new URI(SCHEME + hostAddress);

        Server server = new Server(hostUri.getHost(), hostUri.getPort(), zkAddress);
        server.start();
        System.in.read();
        server.terminate();
    }
}
//http://localhost:8082/?url=http://rambler.ru&count=20