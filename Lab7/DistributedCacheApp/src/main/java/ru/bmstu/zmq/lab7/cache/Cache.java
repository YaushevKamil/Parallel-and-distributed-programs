package ru.bmstu.zmq.lab7.cache;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Cache {
    private String address; // ?????????
    private Storage storage;
    private ZContext context;
    private ZMQ.Socket dealer;
    private ZMQ.Poller poller;

    public Cache(ZContext context, String address, int leftBound, int rightBound, int initialValue) {

    }
}
