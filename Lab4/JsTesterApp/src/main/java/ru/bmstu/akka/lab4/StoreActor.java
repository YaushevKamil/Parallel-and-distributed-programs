package ru.bmstu.akka.lab4;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import ru.bmstu.akka.lab4.messages.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoreActor extends AbstractActor {
    private Map<String, ArrayList<String>> store = new HashMap<>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
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
                .match(GetMessage.class, req -> {
                    String packageId = req.getPackageId();
                    sender().tell(new ResponseMessage(packageId, store.get(packageId)), this.getSelf());
                })
                .build();
    }
}