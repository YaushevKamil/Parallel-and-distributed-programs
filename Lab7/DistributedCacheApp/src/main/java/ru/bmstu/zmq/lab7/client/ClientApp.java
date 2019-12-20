package ru.bmstu.zmq.lab7.client;

import org.zeromq.ZContext;

public class ClientApp {
    public static void main(String[] args) {
        ZContext context = new ZContext();
        Client client = new Client(context, );
    }
}
