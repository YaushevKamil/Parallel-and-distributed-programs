package ru.bmstu.zmq.lab7.proxy;

import org.zeromq.*;
import ru.bmstu.zmq.lab7.command.Command;

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
                ZMsg msg = ZMsg.recvMsg(clientRouter);
                System.out.println("Client message: " +  msg.toString());
                ZFrame clientId = msg.pop();
                msg.pop();
                Command cmd = new Command(msg.popString());
                
            } else if (poller.pollin(1)) { // cache
                ZMsg msg = ZMsg.recvMsg(cacheRouter);
                System.out.println("Cache message: " +  msg.toString());
            }
        }
    }

}
