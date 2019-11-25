package ru.bmstu.akka.lab5.Messages;

public class StoreMessage {
    private final String[] servers;

    public StoreMessage(String[] servers) {
        this.servers = servers;
    }

    public String[] getServers() {
        return servers;
    }
}
