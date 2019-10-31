package ru.bmstu.akka.lab4;

import akka.actor.AbstractActor;

public class PerformActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return Recieve;
    }
}
