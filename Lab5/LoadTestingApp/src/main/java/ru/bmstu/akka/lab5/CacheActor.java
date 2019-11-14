package ru.bmstu.akka.lab5;

import akka.actor.AbstractActor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CacheActor extends AbstractActor {
    private Map<String, ArrayList<Integer>> cache = new HashMap<>();

    @Override
    public Receive createReceive() {
        return null;
    }
}
