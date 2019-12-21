package ru.bmstu.zmq.lab7.cache;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Cache {
    private static final int POLLER_SIZE = 1;
    private static final int PROXY_POLL = 0;

    private String address; // ?????????
    private ZContext context;
    private ZMQ.Socket dealer;
    private ZMQ.Poller poller;
    private Storage storage;

    public Cache(ZContext context, String address, int leftBound, int rightBound, int initialValue) {
        this.address = address;
        this.context = context;
        this.dealer = context.createSocket(SocketType.DEALER);
        this.poller = context.createPoller(POLLER_SIZE);
        this.storage = new Storage(leftBound, rightBound, initialValue);
    }

    private void connect() {
        dealer.connect(address);
    }

    public void setupDealer() {
        poller.register(dealer, ZMQ.Poller.POLLIN);
    }

    public void start() {
        connect();
        setupDealer();
        

    }



    public void terminate() {
        
    }
}
