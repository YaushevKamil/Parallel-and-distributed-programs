package ru.bmstu.akka.lab5.Actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import ru.bmstu.akka.lab5.Messages.StoreMessage;

public class StoreActor extends AbstractActor {
    private String[] servers;

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(StoreMessage.class, m -> );
    }
}
