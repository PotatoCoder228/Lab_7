package ru.potatocoder228.itmo.lab6.connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

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

    public void sendMessage(AskMsg msg) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(msg);
            //oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMap(HashMap<?, ?> map) throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        os.writeObject(map);
        //os.close();
    }
}
