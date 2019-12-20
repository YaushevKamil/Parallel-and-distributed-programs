package ru.bmstu.zmq.lab7.command;

//import java.util.regex.Pattern;

import java.util.ArrayList;

public class Command {
    public enum CommandType {
        GET, PUT, NOTIFY, ERROR
    }

    private static final int INT_ZERO = 0;
    private static final String DELIMITER = "\\s+";
    private ArrayList<Integer> args;
    private CommandType type;

    public Command(String raw) {

    }

    private String[] parseCommand(String raw) {
        //Pattern.matches("А.+а","");
        String[] sepCmd = raw.trim().split(DELIMITER);
        if (sepCmd.length == 0) return null;
        switch (sepCmd[0]) {
            case "GET":
                this.type = CommandType.GET;
                if (sepCmd.length > 1) {
                    args.add(strToInt(sepCmd[1]));
                }
            case "PUT":
                this.type = CommandType.PUT;
            case "NOTIFY":
                this.type = CommandType.NOTIFY;
            case "ERROR":
                this.type = CommandType.ERROR;
        }
    }

    private static int strToInt(String numString) {
        return numString.length() > 0 ?
                Integer.parseInt(numString) :
                INT_ZERO;
    }
//    public getCommand
}
