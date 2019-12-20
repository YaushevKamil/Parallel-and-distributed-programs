package ru.bmstu.zmq.lab7.client;

import org.zeromq.ZContext;

import java.io.PrintStream;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: ClientApp <clientAddress>");
            System.exit(-1);
        }
        String clientAddress = args[0];
        Scanner scanner = new Scanner(System.in);
        PrintStream outputStream = System.out;
        ZContext context = new ZContext();
        
        Client client = new Client(context, clientAddress);
        client.start(scanner, outputStream);
    }
}
