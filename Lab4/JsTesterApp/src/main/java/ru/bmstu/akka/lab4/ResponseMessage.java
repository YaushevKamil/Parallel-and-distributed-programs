package ru.bmstu.akka.lab4;

import java.util.ArrayList;

public class ResponseMessage {
    private String packageId;
    private ArrayList<String> results;

    public ResponseMessage(String packageId, ArrayList<String> results) {
        this.packageId = packageId;
        this.results = results;
    }

    public String getPackageId() {
        return packageId;
    }

    public ArrayList<String> getResults() {
        return results;
    }
}
