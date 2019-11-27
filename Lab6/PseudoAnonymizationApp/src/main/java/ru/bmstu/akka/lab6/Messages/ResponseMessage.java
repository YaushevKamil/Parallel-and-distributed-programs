package ru.bmstu.akka.lab6.Messages;

public class ResponseMessage {
    private final String address;

    public ResponseMessage(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
