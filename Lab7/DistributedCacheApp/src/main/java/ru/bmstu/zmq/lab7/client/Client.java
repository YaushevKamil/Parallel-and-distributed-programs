package ru.bmstu.zmq.lab7.client;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;
import ru.bmstu.zmq.lab7.command.Command;

import java.io.PrintStream;
import java.util.Scanner;

import static ru.bmstu.zmq.lab7.command.Command.Type.SUCCESSFUL;

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
                    Integer result = sendGetMessage();
                case PUT:
                    ;
            }
        }
    }

    public void connect() {
        req.connect(clientAddress);
    }

    private String sendGetMessage(Command cmd) {
        sendMessage(cmd);
        Command respCmd = new Command(receiveMessage());
        return respCmd.getCommandType() == SUCCESSFUL ?
                respCmd.getResult() :
                null;
    }

    private String sendPutMessage(Command cmd) {
        sendMessage(cmd);
        return receiveMessage();
    }

    private void sendMessage(Command cmd) {
        ZMsg msg = ZMsg.newStringMsg(cmd.toString());
        System.out.println("Message to proxy: " + msg);
        msg.send(req);
    }

    private String receiveMessage() {
        ZMsg msg = ZMsg.recvMsg(req);
        System.out.println("Message from proxy: " + msg);
        return msg.popString();
    }

    public void terminate() {
        context.destroySocket(req);
    }
}
