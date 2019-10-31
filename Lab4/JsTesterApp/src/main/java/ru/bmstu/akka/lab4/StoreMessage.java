package ru.bmstu.akka.lab4;

public class StoreMessage {
    private String packageId;
    private String result;

    public StoreMessage(String packageId, String result) {
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