package ru.bmstu.zmq.lab7.client;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Client {
    private String clientAddress;
    private ZContext context;
    private ZMQ.Socket req;

    public Client(ZContext context, String clientAddress) {
        this.clientAddress = clientAddress;
        this.context = context;
        this.req = context
    }
}
