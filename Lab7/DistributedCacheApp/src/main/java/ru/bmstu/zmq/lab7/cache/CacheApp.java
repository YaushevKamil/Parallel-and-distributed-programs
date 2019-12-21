package ru.bmstu.zmq.lab7.cache;

public class CacheApp {
    public static void main(String[] args) {
        if (args.length != 3) {
            usage();
            System.exit(-1);
        }
    }

    private static void usage() {
        System.out.println("Usage: CacheApp <leftBound> <rightBound> <initialValue>");
    }
}
