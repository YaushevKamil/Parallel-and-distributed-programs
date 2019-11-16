package ru.bmstu.akka.lab5;

public class GetMessage {
    private Test test;
    private Long sum;

    public GetMessage(String url, Integer count) {
        this.test = url;
        this.count = count;
    }

    public String getUrl() {
        return url;
    }

    public Integer getCount() {
        return count;
    }
}
