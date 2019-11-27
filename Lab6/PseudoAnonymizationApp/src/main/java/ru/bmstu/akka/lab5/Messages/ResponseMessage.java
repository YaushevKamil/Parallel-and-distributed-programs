package ru.bmstu.akka.lab5.Messages;

public class ResponseMessage {
    private final String address;

    public ResponseMessage(String address) {
        this.address = address;
    }

    public int getRandomNumber() {
        return address;
    }
}
