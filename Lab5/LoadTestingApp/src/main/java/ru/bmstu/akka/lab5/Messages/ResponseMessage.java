package ru.bmstu.akka.lab5.Messages;

public class ResponseMessage {
    private StoreMessage result;

    ResponseMessage(StoreMessage result) {
        this.result = result;
    }

    public StoreMessage getResult() {
        return result;
    }
}
