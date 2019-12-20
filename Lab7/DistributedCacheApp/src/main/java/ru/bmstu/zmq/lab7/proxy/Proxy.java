package ru.bmstu.zmq.lab7.proxy;

import org.zeromq.*;
import ru.bmstu.zmq.lab7.command.Command;

import java.util.Optional;

public class Proxy {
    private static final int POLLER_SIZE = 2;
    private static final int CLIENT_POLL = 0;
    private static final int CACHE_POLL = 1;

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
            if (poller.pollin(CLIENT_POLL)) {
                ZMsg msg = ZMsg.recvMsg(clientRouter);
                System.out.println("Message from client: " +  msg.toString());
                ZFrame clientId = msg.pop();
                msg.pop();
                Command cmd = new Command(msg.popString());
                switch (cmd.getCommandType()) {
                    case GET:
                        Optional<ZFrame>;
                    case PUT:
                        ;
                }
            } else if (poller.pollin(CACHE_POLL)) {
                ZMsg msg = ZMsg.recvMsg(cacheRouter);
                System.out.println("Message from cache: " +  msg.toString());
                ZFrame cacheId = msg.pop();
                Command cmd = new Command(msg.popString());
                switch (cmd.getCommandType()) {
                    case RESULT:
                        ZFrame clientId = msg.pop();
                        sendMessageToClient();
                    case NOTIFY:
                        ;
                }
            }
        }
    }

    private void sendMessageToClient(ZFrame clientId, String result) {
        ZMsg msg = new ZMsg();
        msg.add(clientId);
        msg.add((String) null); // ""
        msg.add(result);
        msg.send(clientRouter); // destroy -> false
    }

    private void sendMessageToCache(ZFrame cacheId, ZFrame clientId, Command cmd) {
        ZMsg msg = new ZMsg();
        msg.add(cacheId);
        msg.add(clientId);
        msg.add(cmd.toString());
        System.out.println("Message to cache: " + msg);
        msg.send(cacheRouter); // destroy -> false
    }

    public void terminate() {
        poller.close();
        context.destroySocket(clientRouter);
        context.destroySocket(cacheRouter);
    }
}
