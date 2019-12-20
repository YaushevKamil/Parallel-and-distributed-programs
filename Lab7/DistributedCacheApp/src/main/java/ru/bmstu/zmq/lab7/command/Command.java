package ru.bmstu.zmq.lab7.command;

//import java.util.regex.Pattern;

import java.util.ArrayList;

public class Command {
    public enum CommandType {
        GET, PUT, NOTIFY, ERROR
    }
    private static final String DELIMITER = "\\s+";
    private ArrayList<Integer> args;
    private CommandType type;

    public Command(String raw) {

    }

    private String[] parseCommand(String raw) {
        //Pattern.matches("А.+а","");
        String[] sepStr = raw.trim().split(DELIMITER);
        if (sepStr.length == 0) return null;
        switch (sepStr[0]) {
            case "GET"
                this.type = CommandType.GET;
                if (sepStr.length > 1) {
                    args.add();
                }

            case "PUT"
                ;
            case "NOTIFY"
                ;
            case "ERROR"
                ;
        }
    }
//    public getCommand
}
