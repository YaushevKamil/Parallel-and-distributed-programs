package ru.bmstu.akka.lab5.Messages;

public class ResponseMessage {
    private StoreMessage;

    ResponseMessage(StoreMessage ) {
        this.url = url;
        this.delay = delay;
    }

    public String getUrl() {
        return url;
    }

    public Long getDelay() {
        return delay;
    }
}
