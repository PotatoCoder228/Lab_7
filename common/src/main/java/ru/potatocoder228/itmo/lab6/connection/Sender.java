package ru.potatocoder228.itmo.lab6.connection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Sender {
    private Socket socket;

    public Sender(Socket socket) {
        this.socket = socket;
    }

    public void sendString(String message) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            //oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendCommand(Object command) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(command);
            //oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
