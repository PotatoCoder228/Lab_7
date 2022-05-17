package ru.potatocoder228.itmo.lab6.main;

import ru.potatocoder228.itmo.lab6.server.Server;

import java.io.IOException;


public class Main
{
    public static void main( String[] args ) throws IOException, ClassNotFoundException {
        System.out.println("Начало работы сервера.");
        int port;
        String path= "";
        try{
            port = Integer.parseInt(args[0]);
            for(int i = 1; i < args.length; i++){
                if (i==1) {
                    path += args[i];
                }else{
                    path += " "+args[i];
                }
            }
            Server server = new Server(port, path);
            server.run();
            System.out.println("Вылетаем...");
        }catch (NumberFormatException e){
            System.out.println("Порт должен быть числом.");
            System.exit(0);
        }catch (IndexOutOfBoundsException e){
            System.out.println("Вы не ввели порт");
            System.exit(0);
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
