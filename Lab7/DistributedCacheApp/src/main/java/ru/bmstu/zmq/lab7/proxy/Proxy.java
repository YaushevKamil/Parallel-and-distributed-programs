package ru.bmstu.zmq.lab7.proxy;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Proxy {
    private String clientAddress;
    private String cacheAddress;
    private ZContext context;
    private ZMQ.Socket clientRouter;
    private ZMQ.Socket cacheRouter;
    private ZMQ.Poller poller;

    public Proxy(ZContext context, String clientAddress, String cacheAddress) {
        this.clientAddress = clientAddress;
        this.cacheAddress = cacheAddress;
        this.context = context;
        this.clientRouter = context.createSocket(SocketType.ROUTER);
        this.cacheRouter = context.createSocket(SocketType.ROUTER);
        this.poller = context.createPoller(2);
        // cache
    }

    public void bind() {
        clientRouter.bind(clientAddress);
        cacheRouter.bind(cacheAddress);
        poller.register(clientRouter, ZMQ.Poller.POLLIN);
        poller.register(cacheRouter, ZMQ.Poller.POLLIN);
    }

    public void createHandler() {
        while(!Thread.currentThread().isInterrupted()) {
            poller.poll();
            if (poller.pollin(0)) { //client // create const
                ZMsg
            } else if (poller.pollin(1)) { // cache

            }
        }
    }

}
