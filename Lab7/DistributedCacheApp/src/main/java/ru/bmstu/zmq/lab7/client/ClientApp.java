package ru.bmstu.zmq.lab7.client;

import org.zeromq.ZContext;

import java.io.PrintStream;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        if (args.length != 2) {
        String clientAddress = args[1];
        Scanner scanner = new Scanner(System.in);
        PrintStream outputStream = System.out;
        ZContext context = new ZContext();
        Client client = new Client(context, clientAddress);
        client.start(scanner, outputStream);
    }
}
