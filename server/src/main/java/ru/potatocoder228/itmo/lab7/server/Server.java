package ru.potatocoder228.itmo.lab7.server;

import ru.potatocoder228.itmo.lab7.commands.*;
import ru.potatocoder228.itmo.lab7.connection.*;
import ru.potatocoder228.itmo.lab7.data.CollectionManager;
import ru.potatocoder228.itmo.lab7.file.FileManager;
import ru.potatocoder228.itmo.lab7.log.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public HashMap<String, String> clientInfo;
    public HashMap<String, String> serverInfo;
    private HashMap<String, Command> clientCommands;
    private HashMap<String, Command> serverCommands;

    private Socket socket;
    private ServerSocket serverSocket;

    private ServerConsole console;
    private CommandManager commandManager;

    private Sender sender;
    private Receiver receiver;
    private RequestHandler requestHandler;

    private ExecutorService request;
    private ExecutorService response;

    public Server(int port, String path) {
        try {
            this.serverSocket = new ServerSocket(port);
            clientCommands = loadClientCommands();
            serverCommands = loadServerCommands();
            FileManager fileManager = new FileManager(path);
            CollectionManager collectionManager = new CollectionManager(fileManager.parseFile());
            collectionManager.setFileManager(fileManager);
            commandManager = new CommandManager(collectionManager);
            console = new ServerConsole(serverCommands, commandManager);

            request = Executors.newCachedThreadPool();
            response = Executors.newCachedThreadPool();

            Log.logger.trace("Начало работы сервера.");
        } catch (IOException e) {
            Log.logger.error("Ошибка подключения. Вероятно, этот порт уже занят. Будет выполнен выход из сервера.");
            Thread.currentThread().interrupt();
        }
    }

    public void run() {
        console.setServerSocket(serverSocket);
        console.start();
        while (true) {
            try {
                synchronized (this) {
                    serverSocket.setSoTimeout(1000);
                    socket = serverSocket.accept();
                    receiver = new Receiver(socket);
                    requestHandler = new RequestHandler(commandManager, clientInfo, clientCommands);
                    sender = new Sender(socket, requestHandler);
                    request.submit(receiver);
                    requestHandler.setReceiver(receiver);
                    requestHandler.start();
                    response.submit(sender);
                }
            } catch (IOException ignored) {
                //
            }
        }
    }

    public HashMap<String, Command> loadServerCommands() {
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