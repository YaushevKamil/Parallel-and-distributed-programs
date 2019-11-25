package ru.bmstu.akka.lab5.Messages;

public class StoreMessage {
    private final String[] addresses;

    public StoreMessage(String[] servers) {
        this.addresses = servers;
    }

    public String[] getAddresses() {
        return addresses;
    }
}
