package ru.bmstu.zmq.lab7.cache;

public class CacheApp {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: CacheApp <leftBound> <rightBound> <initialValue>");
            System.exit(-1);
        }
    }
}
