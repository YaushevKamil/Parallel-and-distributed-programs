package ru.bmstu.akka.lab5.Messages;

public class StoreMessage {
    private final String[] addresses;

    public StoreMessage(String[] addresses) {
        this.addresses = addresses;
    }

    public String[] getAddresses() {
        return addresses;
    }
}
