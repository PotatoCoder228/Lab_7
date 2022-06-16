package ru.potatocoder228.itmo.lab7.connection;

import ru.potatocoder228.itmo.lab7.log.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Receiver implements Runnable{
    private final Socket socket;
    private volatile Ask ask;
    private BlockingQueue<Ask> queue = new LinkedBlockingQueue<>();

    public Receiver(Socket socket) throws IOException {
        this.socket = socket;
    }

    public void run() {
        try {
            synchronized (this) {
                    ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                    ask = (Ask) is.readObject();
                    queue.add(ask);
                    Log.logger.trace("Получен запрос от клиента: " + ask.getMessage());
            }
        }catch(IOException| ClassNotFoundException e){
            Log.logger.error("Некорректный запрос от клиента...");
        }
    }
    public synchronized BlockingQueue<Ask> getAskQueue(){
        return this.queue;
    }
}
