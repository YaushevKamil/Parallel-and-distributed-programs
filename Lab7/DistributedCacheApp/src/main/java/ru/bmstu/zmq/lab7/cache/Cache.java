package ru.bmstu.zmq.lab7.cache;

import org.zeromq.*;
import ru.bmstu.zmq.lab7.command.Command;

import static ru.bmstu.zmq.lab7.command.Command.Type.NOTIFY;
import static ru.bmstu.zmq.lab7.command.Command.Type.RESULT;

public class Cache {
    private static final int POLLER_SIZE = 1;
    private static final int PROXY_POLL = 0;
    private static final int NOTIFY_DURATION_MS = 500;

    private String address; // ?????????
    private ZContext context;
    private ZMQ.Socket dealer;
    private ZMQ.Poller poller;
    private Storage storage;
    private long nextNotifyTime;

    public Cache(ZContext context, String address, int leftBound, int rightBound, int initialValue) {
        this.address = address;
        this.context = context;
        this.dealer = context.createSocket(SocketType.DEALER);
        this.poller = context.createPoller(POLLER_SIZE);
        this.storage = new Storage(leftBound, rightBound, initialValue);
        this.nextNotifyTime = System.currentTimeMillis();
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
        handle();
    }

    public void handle() {
        sendNotifyMessage();
        while (!Thread.currentThread().isInterrupted()) {
            long currentTime = System.currentTimeMillis();
            System.out.println("time: " + (nextNotifyTime - currentTime));
            poller.poll(nextNotifyTime - currentTime);
            if (poller.pollin(PROXY_POLL)) {
                ZMsg msg = ZMsg.recvMsg(dealer);
                System.out.println("Message from proxy: " + msg);
                ZFrame clientId = msg.pop();
                Command cmd = new Command(msg.toString());
                switch (cmd.getCommandType()) {
                    case GET:
                        ZMsg reply = new ZMsg();
                        int reqIndex = cmd.getIndex();
                        int result = storage.get(reqIndex);
                        reply.add(new Command(RESULT, result).toString());
                        reply.add(clientId);
                        reply.send(dealer);
                    case PUT:
                        int index = cmd.getIndex();
                        int value = cmd.getValue();
                        storage.put(index, value);
                }
                currentTime = System.currentTimeMillis();
                if (nextNotifyTime < currentTime) {
                    sendNotifyMessage();
                }
            }
        }
    }

    private void sendNotifyMessage() {
        int firstIndex = storage.getFirstInd();
        int lastIndex = storage.getLastInd();
        ZMsg msg = new ZMsg();
        msg.add(new Command(NOTIFY, firstIndex, lastIndex).toString());
        msg.send(dealer);
        nextNotifyTime += NOTIFY_DURATION_MS;
    }

    public void terminate() {
        poller.close();
        context.destroySocket(dealer);
    }
}