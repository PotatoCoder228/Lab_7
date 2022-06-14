package ru.potatocoder228.itmo.lab7.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver {
    private final Socket socket;

    public Receiver(Socket socket) throws IOException {
        this.socket = socket;
    }

    public AnswerMsg receiveCommand() throws IOException, ClassNotFoundException {
        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        return (AnswerMsg) is.readObject();
    }
}
