package ru.bmstu.akka.lab4;

public class Message {
    private String packageId;
    private String result;

    public Message(String packageId, String result) {
        this.packageId = packageId;
        this.result = result;
    }

    public String getPackageId() {
        return packageId;
    }

    public String getResult() {
        return result;
    }
}
