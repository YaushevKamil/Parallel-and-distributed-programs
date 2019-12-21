package ru.bmstu.zmq.lab7.cache;

import ru.bmstu.zmq.lab7.command.Command;

import java.util.regex.Pattern;

public class CacheApp {
    private static final String NUM_PATTERN = "\\d+";

    public static void main(String[] args) {
        if (args.length != 3) {
            usage();
            System.exit(-1);
        }
        Pattern numPattern = Pattern.compile(NUM_PATTERN);
        
    }

    private static void usage() {
        System.out.println("Usage: CacheApp <leftBound> <rightBound> <initialValue>");
    }

    private boolean isNumeric(String numString, Pattern numPattern) {
        return numString != null && numPattern.matcher(numString).matches();
    }

    private int strToInt(String numString) {
        return isNumeric(numString) ?
                Integer.parseInt(numString) :
                0;
    }
}
