package ru.bmstu.akka.lab5;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CacheActor extends AbstractActor {
    private Map<String, Long> cache = new HashMap<>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder
                .create()
                .match(StoreMessage.class, m -> {
                    String packageId = m.getPackageId();
                    String result = m.getResult();
                    if (store.containsKey(packageId)) {
                        store.get(packageId).add(result);
                    } else {
                        ArrayList<String> results = new ArrayList<>();
                        results.add(result);
                        store.put(packageId, results);
                    }
                })
                .match()
                .build();
    }
}
