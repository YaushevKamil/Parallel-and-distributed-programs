package ru.bmstu.akka.lab4.messages;

public class GetMessage extends Message {
    public GetMessage(String packageId) {
        super(packageId);
    }
}