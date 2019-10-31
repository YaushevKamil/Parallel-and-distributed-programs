package ru.bmstu.akka.lab4.messages;

import java.util.ArrayList;

public class ResponseMessage extends Message {
    private ArrayList<String> results;

    public ResponseMessage(String packageId, ArrayList<String> results) {
        super(packageId);
        this.results = results;
    }

    public ArrayList<String> getResults() {
        return results;
    }
}