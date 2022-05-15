package ru.potatocoder228.itmo.lab6;
import ru.potatocoder228.itmo.lab6.connection.AnswerMsg;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class Main {
    static Selector selector;
    static SocketChannel socketChannel;
    public static void main( String[] args ) throws IOException {
        AnswerMsg msg = new AnswerMsg();
        InetAddress host = InetAddress.getLocalHost();
        int port = Integer.parseInt(args[0]);
        Main.startConnection(host, port);
        Main.sendString(msg);
        ByteBuffer outBuffer = ByteBuffer.wrap("String".getBytes(StandardCharsets.UTF_8));
    }
    public static void startConnection(InetAddress host, int port) throws IOException{
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
}
