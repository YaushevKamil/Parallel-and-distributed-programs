package ru.bmstu.akka.lab5;

public class CacheMessage {
    private String url;
    private Integer count;

    public CacheMessage(String url, Integer count) {
        this.url = url;
        this.count = count;
    }

    public String getUrl() {
        return url;
    }

    public Integer getCount() {
        return count;
    }
}
