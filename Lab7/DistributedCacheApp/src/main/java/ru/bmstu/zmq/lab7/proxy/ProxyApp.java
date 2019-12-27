package ru.bmstu.zmq.lab7.proxy;

import org.zeromq.ZContext;

public class ProxyApp {
    public static void main(String[] args) {
        ZContext context = new ZContext();
        String clientAddress = "tcp://localhost:1050"; // Temporary
        String cacheAddress = "tcp://localhost:1052";  // Temporary
        Proxy proxy = new Proxy(context, clientAddress, cacheAddress);
        proxy.start();
        proxy.terminate();
    }
}
