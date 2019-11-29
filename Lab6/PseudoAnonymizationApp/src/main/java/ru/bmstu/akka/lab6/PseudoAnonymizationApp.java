package ru.bmstu.akka.lab6;

import akka.http.javadsl.model.Uri;
import org.apache.zookeeper.KeeperException;
import ru.bmstu.akka.lab6.Anonymizer.Server;

import java.io.IOException;

import static ru.bmstu.akka.lab6.Anonymizer.ServerRoutes.*;

public class PseudoAnonymizationApp {
    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        if (args.length != 2) {
            System.out.println("Usage: PseudoAnonymizationApp <zkAddr> <anonAddr>");
            System.exit(-1);
        }
        String zkAddress = args[0];
        String hostAddress = args[1];
        Uri hostUri = getUri(hostAddress);

        Server server = new Server(hostUri.getHost().toString(), hostUri.getPort(), zkAddress);
        server.start();
        System.in.read();
        server.terminate();
    }
}
//http://localhost:8082/?url=http://rambler.ru&count=20