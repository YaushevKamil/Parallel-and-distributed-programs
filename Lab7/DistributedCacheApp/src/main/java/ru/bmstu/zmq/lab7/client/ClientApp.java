package ru.bmstu.zmq.lab7.client;

import org.zeromq.ZContext;

import java.io.PrintStream;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        String clientAddress = args[1];
        ZContext context = new ZContext();
        Scanner scanner = new Scanner(System.in);
        PrintStream output = System.out;
        Client client = new Client(context, clientAddress);
        client.start(scanner, output);
    }
}
