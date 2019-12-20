package ru.bmstu.zmq.lab7.client;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String clientAddress;
    private ZContext context;
    private ZMQ.Socket req;

    public Client(ZContext context, String clientAddress) {
        this.clientAddress = clientAddress;
        this.context = context;
        this.req = context.createSocket(SocketType.REQ);
    }

    public void start(Scanner scanner, Print) {
        connect();
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("[Client]$: ");

        }
    }

    public void connect() {
        req.connect(clientAddress);
    }
}
