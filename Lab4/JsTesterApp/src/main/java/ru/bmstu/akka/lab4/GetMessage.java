package ru.bmstu.akka.lab4;

public class GetMessage extends Message {
    private String packageId;

    public GetMessage(String packageId) {
        super(packageId);
    }
//
//    public String getPackageId() {
//        return packageId;
//    }
}