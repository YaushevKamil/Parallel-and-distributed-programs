package ru.bmstu.akka.lab4;

import akka.actor.AbstractActor;

import java.util.ArrayList;
import java.util.HashMap;

public class StoreActor extends AbstractActor {
    private Map<String, ArrayList<Test>> storage = new HashMap<>();
    
    @Override
    public Receive createReceive() {
        return null;
    }
}
