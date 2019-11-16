package ru.bmstu.akka.lab5.Messages;

import ru.bmstu.akka.lab5.Test;

public class StoreMessage {
    private Test test;
    private Long delay;

    public StoreMessage(Test test, Long delay) {
        this.test = test;
        this.delay = delay;
    }

    public Test getTest() {
        return test;
    }

    public Long getDelay() {
        return delay;
    }
}
