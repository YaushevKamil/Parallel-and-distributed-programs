package ru.bmstu.zmq.lab7.proxy;

import org.zeromq.ZContext;

public class Proxy {
    private final String clientAddress;
    private final String cacheAddress;
    private ZContext context;

    public Proxy(ZContext context, String clientAddress, String cacheAddress) {
        this.clientAddress = clientAddress;
        this.cacheAddress = cacheAddress;
        this.context = context;
    }

}
