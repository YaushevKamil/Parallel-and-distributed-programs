package ru.bmstu.zmq.lab7.client;

import org.zeromq.ZContext;

import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        String clientAddress = args[1];
        ZContext context = new ZContext();
        Scanner scanner = new Scanner(System.in);
        Client client = new Client(context, clientAddress, scanner);
    }
}
