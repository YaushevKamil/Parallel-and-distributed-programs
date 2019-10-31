package ru.bmstu.akka.lab4;

import java.util.ArrayList;

public class ResponseMessage {
    private ArrayList<String> results;

    public ResponseMessage(String packageId, ArrayList<String> results) {
        super(packageId);
        this.results = results;
    }

    public String getPackageId() {
        return packageId;
    }

    public ArrayList<String> getResults() {
        return results;
    }
}