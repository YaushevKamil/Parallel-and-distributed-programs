package ru.bmstu.zmq.lab7.command;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Command {
    public enum Type {
        GET, PUT, NOTIFY, RESULT, SUCCESSFUL, ERROR
    }

    private static final int INT_ZERO = 0;
    private static final String DELIMITER = "\\s+";
    private static final String NUM_PATTERN = "\\d+";
    private List<Integer> args;
    private Type type;
    private Pattern numPattern = Pattern.compile(NUM_PATTERN);

    public Command(Type type, Integer ...args) {
        this.type = type;
        this.args = new ArrayList<>();
        Collections.addAll(this.args, args);
    }

    public Command(String raw) {
        this.args = new ArrayList<>();
        parseCommand(raw);
    }

    private void parseCommand(String raw) {
        String[] sepCmd = raw.trim().split(DELIMITER);
        if (sepCmd.length == 0) return;
        switch (sepCmd[0]) {
            case "GET":
                this.type = Type.GET;
                if (sepCmd.length > 1) {
                    args.add(strToInt(sepCmd[1]));
                } else {
                    args.add(INT_ZERO);
                }
                break;
            case "PUT":
                this.type = Type.PUT;
                if (sepCmd.length > 2) {
                    args.add(strToInt(sepCmd[1]));
                    args.add(strToInt(sepCmd[2]));
                } else {
                    args.add(INT_ZERO, INT_ZERO);
                }
                break;
            case "NOTIFY":
                this.type = Type.NOTIFY;
                if (sepCmd.length > 2) {
                    args.add(strToInt(sepCmd[1]));
                    args.add(strToInt(sepCmd[2]));
                } else {
                    args.add(INT_ZERO, INT_ZERO);
                }
                break;
            case "RESULT":
                this.type = Type.RESULT;
                if (sepCmd.length > 1) {
                    args.add(strToInt(sepCmd[1]));
                } else {
                    args.add(INT_ZERO);
                }
                break;
            case "SUCCESSFUL":
                this.type = Type.SUCCESSFUL;
                if (sepCmd.length > 1) {
                    args.add(strToInt(sepCmd[1]));
                } else {
                    args.add(INT_ZERO);
                }
                break;
            case "ERROR":
                this.type = Type.ERROR;
                break;
        }
//        System.out.print(type + " ");
//        for (int arg: args) {
//            System.out.print(arg + " ");
//        }
//        System.out.println();
    }

    public Type getCommandType() {
        return this.type;
    }

    private boolean isNumeric(String numString) {
        return numString != null && numPattern.matcher(numString).matches();
    }

    private int strToInt(String numString) {
        return isNumeric(numString) ?
                Integer.parseInt(numString) :
                INT_ZERO;
    }

    public Integer getIndex() {
        return (this.type == Type.GET  && args.size() == 1) ||
               (this.type == Type.PUT  && args.size() == 2) ?
                args.get(0) :
                null;
    }

    public Integer getValue() {
        return this.type == Type.PUT  && args.size() == 2 ?
                args.get(1) :
                null;
    }

    public Integer getFirstIndex() {
        return this.type == Type.NOTIFY  && args.size() == 2 ?
                args.get(0) :
                null;
    }

    public Integer getLastIndex() {
        return this.type == Type.NOTIFY  && args.size() == 2 ?
                args.get(1) :
                null;
    }

    public Integer getResult() {
        return (this.type == Type.RESULT || this.type == Type.SUCCESSFUL)  && args.size() == 1 ?
                args.get(0) :
                null;
    }

    @Override
    public String toString() {
        return args
                .stream()
                .map(arg -> " " + arg)
                .collect(Collectors.joining("", type.toString(), ""));
    }
}
