package ru.bmstu.akka.lab5.Messages;

public class GetMessage {
    private String url;
    private Integer count;

    public GetMessage(String url, Integer count) {
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
