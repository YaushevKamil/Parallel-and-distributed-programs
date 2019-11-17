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
                .match(StoreMessage.class, msg -> cache.put(msg.getTest(), msg.getDelay()))
                .match(GetMessage.class, msg -> sender()
                            .tell(new ResponseMessage(new StoreMessage(msg, cache.get(msg))),
                                    getSelf()))
                .build();
    }
}
