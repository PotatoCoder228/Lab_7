package ru.potatocoder228.itmo.lab6.server;

import ru.potatocoder228.itmo.lab6.commands.*;
import ru.potatocoder228.itmo.lab6.connection.AnswerMsg;
import ru.potatocoder228.itmo.lab6.connection.AskMsg;
import ru.potatocoder228.itmo.lab6.connection.Receiver;
import ru.potatocoder228.itmo.lab6.connection.Sender;
import ru.potatocoder228.itmo.lab6.data.CollectionManager;
import ru.potatocoder228.itmo.lab6.file.FileManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Server {
    private ServerSocket serverSocket;
    private String filename;
    private Map<String, String> info;

    public Server(int port, String path) {
        try {
            this.serverSocket = new ServerSocket(port);
            this.filename = path;
        } catch (IOException e) {
            System.out.println("Ошибка подключения. Будет выполнен выход из сервера.");
            System.exit(0);
        }
    }

    public void run() {
        HashMap<String, Command> commands = loadCommand();
        CommandManager commandManager;
        FileManager fileManager = new FileManager(filename);
        CollectionManager collectionManager = new CollectionManager(fileManager.parseFile());
        System.out.print("Введите команду:");
        commandManager = new CommandManager(collectionManager);
        ServerConsole console = new ServerConsole(commands, commandManager);
        //offer:
        while (true) {
            try {
                console.parseCommand();
                serverSocket.setSoTimeout(5);
                Socket socket = serverSocket.accept();
                socket.setSoTimeout(1000);
                System.out.println("\nПолучен запрос от клиента.");
                Sender sender = new Sender(socket);
                Receiver receiver = new Receiver(socket);
                commandManager.setMap(commands);
                //try {
                //    commands = loadCommand();
                //    sender.sendMap(commands);
                //}catch (IOException e){
                //    sender.sendString("Ошибка сервера. Проверьте его и запустите ещё раз.");
                //    stopConnection(socket);
                //    continue offer;
                //}
                boolean work = true;
                while (work) {
                    AnswerMsg msg = receiver.receiveCommand();
                    String command = msg.getMessage();
                    System.out.println(command);
                    String[] commandExecuter = command.split("\\s+");
                    if (commandExecuter.length == 2) {
                        String clientMessage = commandManager.run(commandExecuter[0], commandExecuter[1], commands);
                        //System.out.println(clientMessage);
                        AskMsg mesg = new AskMsg();
                        mesg.setMessage(clientMessage);
                        sender.sendMessage(mesg);
                    } else if (commandExecuter.length == 1) {
                        String clientMessage = commandManager.run(commandExecuter[0], "", commands);
                        System.out.println(clientMessage);//TODO
                        AskMsg mesg = new AskMsg();
                        mesg.setMessage(clientMessage);
                        sender.sendMessage(mesg);
                    } else {
                        String message = "Некорректная команда";
                        System.out.println(message);
                        sender.sendString(message);
                    }
                    System.out.printf("Введите команду:");
                }
            } catch (IOException ignored) {
                //
            } catch (ClassNotFoundException e) {
                System.out.println("При десериализации не смог найти класс.");
            }catch (NoSuchElementException e){
                System.out.println("Некорректный ввод, попробуйте снова.");
                run();
            }
        }
    }

    public void stopConnection(Socket s) {
        try {
            s.close();
        } catch (IOException ignored) {
        }
    }

    public HashMap<String, Command> loadCommand() {
        HashMap<String, Command> commands = new HashMap<>();
        info = new HashMap<>();
        new Add(info, commands);
        new AddIfMax(info, commands);
        new Clear(info, commands);
        new ExecuteScript(info, commands);
        new Exit(info, commands);
        new FilterGreaterDescription(info, commands);
        new FilterLessCave(info, commands);
        new Help(info, commands);
        new Info(info, commands);
        new PrintFieldSpeaking(info, commands);
        new RemoveById(info, commands);
        new RemoveFirst(info, commands);
        new RemoveGreater(info, commands);
        new Show(info, commands);
        new UpdateId(info, commands);
        new Save(info, commands);
        return commands;
    }
}