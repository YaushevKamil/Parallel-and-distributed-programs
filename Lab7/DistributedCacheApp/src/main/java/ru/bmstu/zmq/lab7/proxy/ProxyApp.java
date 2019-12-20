package ru.bmstu.zmq.lab7.proxy;

import org.zeromq.ZContext;

public class ProxyApp {
    public static void main(String[] args) {
        ZContext context = new ZContext();
        String clientAddress;
        String cacheAddress;
        Proxy proxy = new Proxy(context, clientAddress, cacheAddress);
    }
}