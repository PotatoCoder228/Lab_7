package ru.potatocoder228.itmo.lab6;

import ru.potatocoder228.itmo.lab6.connection.AnswerMsg;
import ru.potatocoder228.itmo.lab6.connection.AskMsg;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    static Selector selector;
    static SocketChannel socketChannel;
    static AskMsg message = new AskMsg();

    public static void main(String[] args) throws IOException {
        AnswerMsg msg = new AnswerMsg();
        InetAddress host = InetAddress.getLocalHost();
        int port = Integer.parseInt(args[0]);
        while (true) {
            Scanner scanner = new Scanner(System.in);
            if(scanner.nextLine().length()>1) {
                Main.startConnection(host, port);
                Main.sendString(msg);
                try {
                    AskMsg msg1 = Main.receiveObject();
                    System.out.println(msg1.getMessage());
                }catch (IOException e){
                    AskMsg msg1 = Main.receiveObject();
                    System.out.println(msg1.getMessage());
                }
            }
        }
    }

    public static void startConnection(InetAddress host, int port) throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open();
        System.out.println("Открыли сокет");
        socketChannel.configureBlocking(false);
        System.out.println("Неблокирующий режим");
        socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE | SelectionKey.OP_READ);
        System.out.println("Зарегали сокет");
        socketChannel.connect(new InetSocketAddress(host, port));
        System.out.println("ПОДКЛЮЧИЛИСЬ!!!");
        while (true) {
            selector.select();
            System.out.println("Селектнули");
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();
                System.out.println("выбрали и удалили ключик");
                if (key.isValid()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    System.out.println("Соединение успешно установлено.");
                    if (channel.isConnectionPending()) {
                        channel.finishConnect();
                    }
                    return;
                }
            }
        }
    }

    public static void sendString(AnswerMsg message) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        message.setMessage("help");
        objectOutputStream.writeObject(message);
        ByteBuffer outBuffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        System.out.println("Перевели объект в массив байтов");
        while (true) {
            selector.select();
            System.out.println("Селектнули при отправке");
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();
                if (key.isValid() && key.isWritable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    System.out.println("Открываем новый канал.");
                    channel.write(outBuffer);
                    System.out.println("Записали буфер.");
                    if (outBuffer.remaining() < 1) {
                        return;
                    }
                }
            }
        }
    }

    public static AskMsg receiveObject() throws IOException {
            System.out.println("Читаем пришедший ответ...");
                while (true) {
                    selector.select();
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isValid()&&selectionKey.isReadable()) {
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
    //public static AskMsg receiveObject(){
    //    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream();
    //    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
    //}
}
