package ru.bmstu.zmq.lab7.cache;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;
import ru.bmstu.zmq.lab7.command.Command;

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
        sendNotifyMessage();
        while (!Thread.currentThread().isInterrupted()) {
            long currentTime = System.currentTimeMillis();
            System.out.println("time: " + (nextNotifyTime - currentTime));
            poller.poll(nextNotifyTime - currentTime);
            if (poller.pollin()) {

            }
        }
    }

    private void sendNotifyMessage() {
        ZMsg.newStringMsg(new Command(Command.Type.NOTIFY, storage.getFirstInd(), storage.getLastInd()).toString());
        nextNotifyTime += NOTIFY_DURATION_MS;
    }

    public void terminate() {
        poller.close();
        context.destroySocket(dealer);
    }
}
