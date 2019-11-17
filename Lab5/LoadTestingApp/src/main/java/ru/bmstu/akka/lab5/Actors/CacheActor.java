package ru.bmstu.akka.lab5.Actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import ru.bmstu.akka.lab5.Messages.*;
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
                .match(StoreMessage.class, msg -> cache.put(msg.getTest(), msg.getDelay()))
                .match(GetMessage.class, msg -> {
                    String url = msg.getUrl();
                    Integer count = msg.getCount();
                    sender().tell(new ResponseMessage(new StoreMessage(url, cache.get(url))), getSelf());
                })
                .build();
    }
}
