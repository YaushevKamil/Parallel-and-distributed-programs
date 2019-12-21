package ru.bmstu.zmq.lab7.cache;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Cache {
    private String address; // ?????????
    private ZContext context;
    private ZMQ.Socket dealer;
    private ZMQ.Poller poller;
    private Storage storage;

    public Cache(ZContext context, String address, int leftBound, int rightBound, int initialValue) {
        this.context = context;
        this.address = address;
        this.storage = new Storage(leftBound, rightBound, initialValue);
    }

    public void start() {
        connect();

    }

    private void connect() {
        dealer.connect(address);
    }

    public void terminate() {
        
    }
}
