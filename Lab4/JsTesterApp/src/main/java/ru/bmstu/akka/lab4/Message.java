package ru.bmstu.akka.lab4;

public class Message {
    private String packageId;

    public Message(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageId() {
        return packageId;
    }
}
