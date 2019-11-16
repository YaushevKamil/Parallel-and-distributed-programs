package ru.bmstu.akka.lab5.Actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import ru.bmstu.akka.lab5.Messages.StoreMessage;
import ru.bmstu.akka.lab5.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CacheActor extends AbstractActor {
    private Map<Test, Long> cache = new HashMap<>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder
                .create()
                .match(StoreMessage.class, msg -> {
                    Test test = msg.getTest();
                    Long delay = msg.getDelay();
                    cache.put(test, delay);
                })
                .match(GetMessage.class, m -> {
                    String url = m.getUrl();
                    sender().tell(new ResponseMessage(url, cache.get(url).get(0)), getSelf());
                })
                .build();
    }
}
