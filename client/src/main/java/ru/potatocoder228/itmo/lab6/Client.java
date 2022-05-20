package ru.potatocoder228.itmo.lab6;

import ru.potatocoder228.itmo.lab6.connection.AnswerMsg;
import ru.potatocoder228.itmo.lab6.connection.AskMsg;
import ru.potatocoder228.itmo.lab6.exceptions.RecursiveScriptExecuteException;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Client {
    private SocketChannel socketChannel;
    private Selector selector;
    private AnswerMsg answerMsg = new AnswerMsg();
    private AskMsg askMsg = new AskMsg();
    private InetAddress host;
    private int port;

    public Client(int port) {
        try {
            this.port = port;
            this.host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        try {
            ClientConsole clientConsole = new ClientConsole(false);
            while (true) {
                answerMsg = clientConsole.inputCommand();
                if (answerMsg.getMessage().equals("exit")) {
                    System.out.println("Завершение работы приложения...");
                    socketChannel.close();
                    System.exit(0);
                }
                startConnection(host, port);
                sendMessage(answerMsg);
                AskMsg msg1 = receiveObject();
                System.out.println(msg1.getMessage());
            }
        }catch (RecursiveScriptExecuteException e){
            run();
        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка при получении ответа от сервера. Возможно, он временно недоступен.");
            run();
            //while (true) {
            //    Scanner scanner = new Scanner(System.in);
            //    if (scanner.nextLine().length() > 1) {
            //        startConnection(host, port);
            //        sendMessage(msg);
            //        try {
            //            AskMsg msg1 = receiveObject();
            //            System.out.println(msg1.getMessage());
            //        } catch (IOException e) {
            //            System.out.println("Ошибка при получении сообщения");
            //        }
            //    }
            //}
        }
    }

    public void startConnection(InetAddress host, int port) throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE | SelectionKey.OP_READ);
        socketChannel.connect(new InetSocketAddress(host, port));
        while (true) {
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();
                if (key.isValid()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    if (channel.isConnectionPending()) {
                        channel.finishConnect();
                    }
                    return;
                }
            }
        }
    }

    public void sendMessage(AnswerMsg msg) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(msg);
        ByteBuffer outBuffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        System.out.println("Отправляем запрос на сервер.");
        while (true) {
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();
                if (key.isValid() && key.isWritable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.write(outBuffer);
                    if (outBuffer.remaining() < 1) {
                        return;
                    }
                }
            }
        }
    }

    public AskMsg receiveObject() throws IOException {
        System.out.println("Читаем пришедший ответ...");
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isValid() && selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(100000);
                    ByteBuffer outBuffer = ByteBuffer.allocate(100000);
                    try {
                        while (socketChannel.read(byteBuffer) > 0) {
                            byteBuffer.flip();
                            outBuffer.put(byteBuffer);
                            byteBuffer.compact();
                            try {
                                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(outBuffer.array()));
                                return (AskMsg) objectInputStream.readObject();
                            } catch (StreamCorruptedException ignored) {
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        if (socketChannel.read(byteBuffer) == -1) {
                            throw new IOException();
                        }
                    } catch (IOException e) {
                        throw e;
                    }
                }
                iterator.remove();
            }
        }
    }
}
