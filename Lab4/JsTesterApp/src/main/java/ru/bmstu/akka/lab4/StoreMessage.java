package ru.bmstu.akka.lab4;

public class StoreMessage extends Message {
    private String result;

    public StoreMessage(String packageId, String result) {
        super(packageId);
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}