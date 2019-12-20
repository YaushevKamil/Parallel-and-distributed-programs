package ru.bmstu.zmq.lab7.command;

//import java.util.regex.Pattern;

public class Command {
    private static final String DELIMITER = "\\s+";

    public enum CommandType {
        GET, PUT, NOTIFY, ERROR
    }

    public Command(String raw) {

    }

    private String[] parseCommand(String raw) {
        //Pattern.matches("А.+а","");
        String[] separatedString = raw.trim().split(DELIMITER);
    }
//    public getCommand
}
