package ru.bmstu.akka.lab5;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CacheActor extends AbstractActor {
    private Map<Test, Long> cache = new HashMap<>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder
                .create()
                .match(CacheMessage.class, m -> {
                    String url = m.getUrl();
                    Long delay = m.getDelay();
                    if (cache.containsKey(url)) {
                        cache.get(url).add(delay);
                    } else {
                        ArrayList<Long> results = new ArrayList<>();
                        results.add(delay);
                        cache.put(url, results);
                    }
                })
                .match(GetMessage.class, m -> {
                    String url = m.getUrl();
                    sender().tell(new ResponseMessage(url, cache.get(url).get(0)), getSelf());
                })
                .build();
    }
}
