package ru.bmstu.akka.lab5.Actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import ru.bmstu.akka.lab5.Messages.GetMessage;
import ru.bmstu.akka.lab5.Messages.ResponseMessage;
import ru.bmstu.akka.lab5.Messages.StoreMessage;

import java.util.Random;

public class StoreActor extends AbstractActor {
    private String[] addresses;
    private Random random;

//    private static int getRandomNumber(int bound) {
//        return new Random().nextInt(bound);
//    }

    public StoreActor() {
        this.random = new Random();
    }

    private int getRandomNumber(int bound) {
        return random.nextInt(bound);
    }

    private String getRandomAddress() {
        return addresses[getRandomNumber(addresses.length)];
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(StoreMessage.class, msg -> addresses = msg.getAddresses())
                .match(GetMessage.class, msg -> sender().tell(
                        new ResponseMessage(),
                        self()))
                .build();
    }
}
