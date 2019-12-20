package ru.bmstu.zmq.lab7.client;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;
import ru.bmstu.zmq.lab7.command.Command;

import java.io.PrintStream;
import java.util.Scanner;

public class Client {
    private String clientAddress;
    private ZContext context;
    private ZMQ.Socket req;

    public Client(ZContext context, String clientAddress) {
        this.clientAddress = clientAddress;
        this.context = context;
        this.req = context.createSocket(SocketType.REQ);
    }

    public void start(Scanner scanner, PrintStream output) {
        connect();
        while (!Thread.currentThread().isInterrupted()) {
            output.print("[Client]$: ");
            Command cmd = new Command(scanner.nextLine());
            switch (cmd.getCommandType()) {
                case GET:
                    ;
                case PUT:
                    ;
            }
        }
    }

    public void connect() {
        req.connect(clientAddress);
    }

    private String sendGetMessage(Command ) {
        ZMsg msg = ZMsg.newStringMsg()
    }

    private String sendPutMessage() {

    }

    public void terminate() {
        context.destroySocket(req);
    }
}
