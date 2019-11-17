package ru.bmstu.akka.lab5.Actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import ru.bmstu.akka.lab5.Messages.*;

import java.util.HashMap;
import java.util.Map;

public class CacheActor extends AbstractActor {
    private Map<GetMessage, Long> cache = new HashMap<>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder
                .create()
                .match(StoreMessage.class, msg -> {
                    System.out.println("ACTOR::" + msg.toString());
                    System.out.println(msg.getTest().getUrl() + " " + msg.getTest().getUrl() + " " + msg.getDelay());
                    cache.put(msg.getTest(), msg.getDelay());
                })
                .match(GetMessage.class, msg -> {
                    System.out.println("ACTOR::" + msg.toString());
                    System.out.println(msg.getUrl() + " " + msg.getCount());
                    sender()
                            .tell(new ResponseMessage(new StoreMessage(msg, cache.get(msg))),
                                    getSelf());
                })
                .build();
    }
}
