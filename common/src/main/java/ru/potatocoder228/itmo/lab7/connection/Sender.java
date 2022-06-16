package ru.potatocoder228.itmo.lab7.connection;

import ru.potatocoder228.itmo.lab7.log.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Sender implements Runnable{
    private Socket socket;
    private volatile Answer answer;
    private InetSocketAddress address;
    private RequestHandler requestHandler;
    private ConcurrentLinkedQueue<Answer> queue = new ConcurrentLinkedQueue<>();

    public Sender(Socket socket, RequestHandler requestHandler) {
        this.socket = socket;
        this.requestHandler = requestHandler;
    }

    public void run() {
        synchronized (RequestHandler.class) {
                queue = requestHandler.getQueue();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!queue.isEmpty()) {
                    try {
                        //this.address = new InetSocketAddress(InetAddress.getLocalHost(), 6000);
                        //socket.bind(address);
                        answer = queue.poll();
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(answer);
                        Log.logger.trace("Сообщение успешно отправлено клиенту.");
                        System.out.println("Введите команду:");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.logger.error(e.getMessage());
                    }
            }
        }
    }
}
