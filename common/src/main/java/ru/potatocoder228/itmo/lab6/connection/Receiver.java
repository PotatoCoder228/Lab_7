package ru.potatocoder228.itmo.lab6.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver {
    private Socket socket;
    public Receiver(Socket socket) throws IOException {
        this.socket = socket;
    }

    public AnswerMsg receiveMessage() throws ClassNotFoundException, IOException {
        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        AnswerMsg result = (AnswerMsg) is.readObject();
        return result;
    }

    public AnswerMsg receiveCommand() throws IOException, ClassNotFoundException {
        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        AnswerMsg command = (AnswerMsg) is.readObject();
        return command;
    }
}
