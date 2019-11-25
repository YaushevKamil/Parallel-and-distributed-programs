package ru.bmstu.akka.lab5.Actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import ru.bmstu.akka.lab5.Messages.GetMessage;
import ru.bmstu.akka.lab5.Messages.ResponseMessage;
import ru.bmstu.akka.lab5.Messages.StoreMessage;

import java.util.Random;

public class StoreActor extends AbstractActor {
    private String[] servers; // RENAME

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(StoreMessage.class, msg -> servers = msg.getServers())
                .match(GetMessage.class, msg -> {
                    sender().tell(new ResponseMessage(new Random().nextInt(servers.length)), self());
                })
                .build();
    }
}
