package ru.potatocoder228.itmo.lab6.main;

import ru.potatocoder228.itmo.lab6.exceptions.ConnectionException;
import ru.potatocoder228.itmo.lab6.log.Log;
import ru.potatocoder228.itmo.lab6.server.Server;


public class Main {
    public static void main(String[] args) {
        System.out.println("Начало работы сервера.");
        int port;
        String path = null;
        try {
            port = Integer.parseInt(args[0]);
            for (int i = 1; i < args.length; i++) {
                if (i == 1) {
                    path = "";
                    path += args[i];
                } else {
                    path += " " + args[i];
                }
            }
            Server server = new Server(port, path);
            server.run();
        } catch (NumberFormatException e) {
            Log.logger.error("Порт должен быть числом. Сервер завершает свою работу.");
            System.exit(0);
        } catch (IndexOutOfBoundsException e) {
            Log.logger.error("Вы не ввели порт. Сервер завершает свою работу.");
            System.exit(0);
        } catch (NullPointerException e) {
            Log.logger.error("Вы не ввели путь к переменной окружения. Сервер завершает свою работу.");
        }


        //OutputStream out;
        //InputStream is;
        //Socket sock;
        //ServerSocket serv;
        //InetAddress host = InetAddress.getLocalHost();
        //int port = Integer.parseInt(args[0]);

        //serv = new ServerSocket(port);
        //
        //while(true){
        //    try{
        //        Socket s = serv.accept();
        //        Sender sender = new Sender(s);
        //        Receiver receiver = new Receiver(s);
        //        try{
        //            System.out.println("Готовы к получению сообщения.");
        //            AnswerMsg msg = receiver.receiveMessage();
        //            System.out.println(msg.getStatus());
        //            System.out.println("Сигнал получен");
        //        }catch(IOException e){
        //            System.out.println("Помогите...");
        //        }
        //    }catch(IOException e){
        //        System.out.println("Клиент отвалился к хренам");
        //    }
        //}
    }
}
