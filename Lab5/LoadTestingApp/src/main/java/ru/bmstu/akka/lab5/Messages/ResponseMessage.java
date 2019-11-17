package ru.bmstu.akka.lab5.Messages;

import java.util.Optional;

public class ResponseMessage {
    private StoreMessage result;

    public ResponseMessage(StoreMessage result) {
        this.result = result;
    }

    public Optional<StoreMessage> getResult() {
        return result.getDelay() != null ? Optional.of(result) : Optional.empty();
    }
}
