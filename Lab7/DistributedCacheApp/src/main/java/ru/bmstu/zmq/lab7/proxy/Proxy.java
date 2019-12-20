package ru.bmstu.zmq.lab7.proxy;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Proxy {
    private String clientAddress;
    private String cacheAddress;
    private ZContext context;
    private ZMQ.Socket clientRouter;
    private ZMQ.Socket clientRouter;

    public Proxy(ZContext context, String clientAddress, String cacheAddress) {
        this.clientAddress = clientAddress;
        this.cacheAddress = cacheAddress;
        this.context = context;
    }

}
