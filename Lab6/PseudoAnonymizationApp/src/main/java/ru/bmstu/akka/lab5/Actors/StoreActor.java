package ru.bmstu.akka.lab5.Actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import ru.bmstu.akka.lab5.Messages.GetMessage;
import ru.bmstu.akka.lab5.Messages.ResponseMessage;
import ru.bmstu.akka.lab5.Messages.StoreMessage;

import java.util.Random;

public class StoreActor extends AbstractActor {
    private String[] servers; // RENAME
    private Random random;

//    private static int getRandomNumber(int bound) {
//        return new Random().nextInt(bound);
//    }

    public StoreActor() {
        this.random = new Random();
    }

    private static int getRandomNumber(Random random, int bound) {
        return random.nextInt(bound);
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(StoreMessage.class, msg -> servers = msg.getServers())
                .match(GetMessage.class, msg -> sender().tell(new ResponseMessage(getRandomNumber(random, servers.length)),
                        self()))
                .build();
    }
}
