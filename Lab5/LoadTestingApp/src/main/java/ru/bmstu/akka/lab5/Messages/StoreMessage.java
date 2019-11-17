package ru.bmstu.akka.lab5.Messages;

import ru.bmstu.akka.lab5.Test;

public class StoreMessage {
    private GetMessage test;
    private Long delay;

    public StoreMessage(GetMessage test, Long delay) {
        this.test = test;
        this.delay = delay;
    }

    public GetMessage getTest() {
        return test;
    }

    public Long getDelay() {
        return delay;
    }
}
