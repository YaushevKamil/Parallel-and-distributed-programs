package ru.bmstu.akka.lab4.messages;

public class Message {
    private String packageId;

    Message(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageId() {
        return packageId;
    }
}
