package ru.bmstu.zmq.lab7.cache;

public class CacheApp {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: CacheApp <clientAddress>");
            System.exit(-1);
        }
    }
}
