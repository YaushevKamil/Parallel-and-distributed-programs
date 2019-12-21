package ru.bmstu.zmq.lab7.cache;

import ru.bmstu.zmq.lab7.command.Command;

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

    private boolean isNumeric(String numString) {
        return numString != null && numPattern.matcher(numString).matches();
    }

    private int strToInt(String numString) {
        return isNumeric(numString) ?
                Integer.parseInt(numString) :
                0;
    }
}
