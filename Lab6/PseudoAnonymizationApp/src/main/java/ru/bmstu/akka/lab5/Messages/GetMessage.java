package ru.bmstu.akka.lab5.Messages;

public class GetMessage {
    private final int randomNumber;

    GetMessage(int randomNumber) {
        this.randomNumber = randomNumber;
    }

    public int getRandomNumber() {
        return randomNumber;
    }
}
