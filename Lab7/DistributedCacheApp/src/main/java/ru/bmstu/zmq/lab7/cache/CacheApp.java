package ru.bmstu.zmq.lab7.cache;

import org.zeromq.ZContext;
import ru.bmstu.zmq.lab7.command.Command;

import java.util.regex.Pattern;

public class CacheApp {
    private static final String NUM_PATTERN = "\\d+";

    public static void main(String[] args) {
        if (args.length != 3) {
            usage();
            System.exit(-1);
        }
        String address = args[0];
        Pattern numPattern = Pattern.compile(NUM_PATTERN);
        int leftBound
        ZContext context = new ZContext();

        Cache cache = new Cache(context, address);
    }

    private static void usage() {
        System.out.println("Usage: CacheApp <address> <leftBound> <rightBound> <initialValue>");
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
