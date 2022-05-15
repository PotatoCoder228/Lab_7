package ru.potatocoder228.itmo.lab6.main;

import ru.potatocoder228.itmo.lab6.connection.AnswerMsg;
import ru.potatocoder228.itmo.lab6.connection.Receiver;
import ru.potatocoder228.itmo.lab6.connection.Sender;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Main
{
    public static void main( String[] args ) throws IOException, ClassNotFoundException {
        System.out.println("Начало работы сервера.");
        OutputStream out;
        InputStream is;
        Socket sock;
        ServerSocket serv;
        InetAddress host = InetAddress.getLocalHost();
        int port = Integer.parseInt(args[0]);

        serv = new ServerSocket(port);

        while(true){
            try{
                Socket s = serv.accept();
                Sender sender = new Sender(s);
                Receiver receiver = new Receiver(s);
                try{
                    System.out.println("Готовы к получению сообщения.");
                    AnswerMsg msg = receiver.receiveMessage();
                    System.out.println(msg.getStatus());
                    System.out.println("Сигнал получен");
                }catch(IOException e){
                    System.out.println("Помогите...");
                }
            }catch(IOException e){
                System.out.println("Клиент отвалился к хренам");
            }
        }


        //AnswerMsg msg = (AnswerMsg) Test.fromBytesToObject(object);
        //System.out.println(msg.getStatus()+"Привет");//TODO 53:27
    }
}
