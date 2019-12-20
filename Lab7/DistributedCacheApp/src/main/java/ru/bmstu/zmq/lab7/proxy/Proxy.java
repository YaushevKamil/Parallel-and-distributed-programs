package ru.bmstu.zmq.lab7.proxy;

import org.zeromq.*;
import ru.bmstu.zmq.lab7.command.Command;

public class Proxy {
    private static final int POLLER_SIZE = 2;
    private static final int CLIENT_POLLIN = 0;
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
        this.poller = context.createPoller(POLLER_SIZE);
        // cache
    }

    public void setupRouter() {
        clientRouter.bind(clientAddress);
        poller.register(clientRouter, ZMQ.Poller.POLLIN);
        cacheRouter.bind(cacheAddress);
        poller.register(cacheRouter, ZMQ.Poller.POLLIN);
    }

    public void start() {
        setupRouter();
        while(!Thread.currentThread().isInterrupted()) {
            poller.poll();
            if (poller.pollin(CLIENT_POLLIN)) { //client // create const
                ZMsg msg = ZMsg.recvMsg(clientRouter);
                System.out.println("Message from client: " +  msg.toString());
                ZFrame clientId = msg.pop();
                msg.pop();
                Command cmd = new Command(msg.popString());
                switch (cmd.getCommandType()) {
                    case GET:
                        ;
                    case PUT:
                        ;
                }
            } else if (poller.pollin(1)) { // cache
                ZMsg msg = ZMsg.recvMsg(cacheRouter);
                System.out.println("Message from cache: " +  msg.toString());
                ZFrame cacheId = msg.pop();
                Command cmd = new Command(msg.popString());
                switch (cmd.getCommandType()) {
                    case RESULT:
                        ;
                    case NOTIFY:
                        ;
                }
            }
        }
    }

    public void terminate() {
        poller.close();
        context.destroySocket(clientRouter);
        context.destroySocket(cacheRouter);
    }
}
