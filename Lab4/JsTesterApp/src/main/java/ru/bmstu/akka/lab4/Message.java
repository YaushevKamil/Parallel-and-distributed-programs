package ru.bmstu.akka.lab4;

public class Message {
    private String packageId;

    public Message(String packageId, String result) {
        this.packageId = packageId;
    }

    public String getPackageId() {
        return packageId;
    }
}
