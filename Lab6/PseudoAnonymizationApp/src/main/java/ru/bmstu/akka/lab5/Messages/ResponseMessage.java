package ru.bmstu.akka.lab5.Messages;

public class ResponseMessage {
    private final int randomNumber;

    public ResponseMessage(int randomNumber) {
        this.randomNumber = randomNumber;
    }

    public int getRandomNumber() {
        return randomNumber;
    }
}
