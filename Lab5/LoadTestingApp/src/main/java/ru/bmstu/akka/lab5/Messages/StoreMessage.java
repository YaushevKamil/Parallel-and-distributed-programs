package ru.bmstu.akka.lab5;

public class CacheMessage {
    private String url;
    private Long delay;
    private float delay2;

    public CacheMessage(String url, Long delay) {
        this.url = url;
        this.delay = delay;
    }

    public CacheMessage(float delay) {
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
