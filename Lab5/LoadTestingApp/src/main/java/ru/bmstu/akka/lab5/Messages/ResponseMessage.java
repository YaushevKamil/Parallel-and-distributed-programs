package ru.bmstu.akka.lab5.Messages;

public class ResponseMessage {
    private String url;
    private Long delay;

    ResponseMessage(String url, Long delay) {
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
