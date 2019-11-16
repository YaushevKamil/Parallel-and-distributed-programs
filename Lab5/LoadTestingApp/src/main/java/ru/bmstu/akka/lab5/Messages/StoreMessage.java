package ru.bmstu.akka.lab5.Messages;

public class StoreMessage {
    private Test test;
    private Long delay;

    public StoreMessage(String url, Long delay) {
        this.url = url;
        this.delay = delay;
    }

    public StoreMessage(float delay) {
        this.url = "";
        this.delay2 = delay2;
    }

    public String getUrl() {
        return url;
    }

    public Long getDelay() {
        return delay;
    }
}
