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
    public HashMap<String, String> clientInfo;
    public HashMap<String, String> serverInfo;
    private ServerConsole console;
    private CommandManager commandManager;
    private HashMap<String, Command> clientCommands;
    private HashMap<String, Command> serverCommands;
    public Server(int port, String path) {
        try {
            this.serverSocket = new ServerSocket(port);
            clientCommands = loadClientCommands();
            serverCommands = loadServerCommands();
            FileManager fileManager = new FileManager(path);
            CollectionManager collectionManager = new CollectionManager(fileManager.parseFile());
            commandManager = new CommandManager(collectionManager);
            console = new ServerConsole(serverCommands, commandManager);
        } catch (IOException e) {
            System.out.println("Ошибка подключения. Будет выполнен выход из сервера.");
            System.exit(0);
        }
    }

    public void run() {
        System.out.print("Введите команду:");
        //offer:
        while (true) {
            try {
                console.parseCommand(serverSocket);
                serverSocket.setSoTimeout(5);
                Socket socket = serverSocket.accept();
                socket.setSoTimeout(1000);
                System.out.println("\nПолучен запрос от клиента.");
                Sender sender = new Sender(socket);
                Receiver receiver = new Receiver(socket);
                commandManager.setMap(clientCommands);
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
                        commandManager.setNewDragon(msg.getDragon());
                        commandManager.setCollectionInfo(clientInfo);
                        String clientMessage = commandManager.clientRun(commandExecuter[0], commandExecuter[1], clientCommands);
                        //System.out.println(clientMessage);
                        AskMsg mesg = new AskMsg();
                        mesg.setMessage(clientMessage);
                        sender.sendMessage(mesg);
                    } else if (commandExecuter.length == 1) {
                        String clientMessage = commandManager.clientRun(commandExecuter[0], "", clientCommands);
                        System.out.println(clientMessage);//TODO
                        AskMsg mesg = new AskMsg();
                        mesg.setMessage(clientMessage);
                        sender.sendMessage(mesg);
                    }
                    System.out.print("Введите команду:");
                }
            } catch (IOException ignored) {
                //
            } catch (ClassNotFoundException e) {
                System.out.println("При десериализации не смог найти класс.");
            }catch (NoSuchElementException e){
                System.out.println("Некорректный ввод, попробуйте снова.");
                run();
            }catch (NullPointerException e){
                System.out.println("Некорректная команда.");
                run();
            }
        }
    }

    public void stopConnection(ServerSocket s) {
        try {
            s.close();
        } catch (IOException ignored) {
        }
    }
    public HashMap<String, Command> loadServerCommands(){
        HashMap<String, Command> commands = new HashMap<>();
        serverInfo = new HashMap<>();
        new Save(serverInfo, commands);
        new Exit(serverInfo, commands);
        return commands;
    }
    public HashMap<String, Command> loadClientCommands() {
        HashMap<String, Command> commands = new HashMap<>();
        clientInfo = new HashMap<>();
        new Add(clientInfo, commands);
        new AddIfMax(clientInfo, commands);
        new Clear(clientInfo, commands);
        new ExecuteScript(clientInfo, commands);
        new Exit(clientInfo, commands);
        new FilterGreaterDescription(clientInfo, commands);
        new FilterLessCave(clientInfo, commands);
        new Help(clientInfo, commands);
        new Info(clientInfo, commands);
        new PrintFieldSpeaking(clientInfo, commands);
        new RemoveById(clientInfo, commands);
        new RemoveFirst(clientInfo, commands);
        new RemoveGreater(clientInfo, commands);
        new Show(clientInfo, commands);
        new UpdateId(clientInfo, commands);
        return commands;
    }
}