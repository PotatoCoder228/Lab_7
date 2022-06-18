package ru.potatocoder228.itmo.lab7.server;

import ru.potatocoder228.itmo.lab7.commands.*;
import ru.potatocoder228.itmo.lab7.connection.Answer;
import ru.potatocoder228.itmo.lab7.connection.Ask;
import ru.potatocoder228.itmo.lab7.connection.ClientStatus;
import ru.potatocoder228.itmo.lab7.connection.Status;
import ru.potatocoder228.itmo.lab7.data.CollectionManager;
import ru.potatocoder228.itmo.lab7.database.DatabaseHandler;
import ru.potatocoder228.itmo.lab7.database.DragonDatabaseManager;
import ru.potatocoder228.itmo.lab7.database.UserDatabaseManager;
import ru.potatocoder228.itmo.lab7.exceptions.DatabaseException;
import ru.potatocoder228.itmo.lab7.log.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.*;

public class Server {
    public HashMap<String, String> clientInfo;
    public HashMap<String, String> serverInfo;
    private HashMap<String, Command> clientCommands;

    private BlockingQueue<Ask> receiverQueue;
    private BlockingQueue<Answer> senderQueue;

    private ServerSocket serverSocket;

    private ServerConsole console;
    private CommandManager commandManager;

    private ExecutorService request;
    private ExecutorService response;

    private UserDatabaseManager userManager;

    public Server(int port, Properties properties) {
        try {

            this.serverSocket = new ServerSocket(port);
            clientCommands = loadClientCommands();
            HashMap<String, Command> serverCommands = loadServerCommands();


            DatabaseHandler databaseHandler = new DatabaseHandler(properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("password"));
            userManager = new UserDatabaseManager(databaseHandler);
            DragonDatabaseManager dragonDatabaseManager = new DragonDatabaseManager(databaseHandler, userManager);
            CollectionManager collectionManager = new CollectionManager();
            collectionManager.setDragonManager(dragonDatabaseManager);
            collectionManager.getDragonManager().deserializeCollection(collectionManager);

            commandManager = new CommandManager(collectionManager);
            console = new ServerConsole(serverCommands, commandManager, databaseHandler);

            request = Executors.newCachedThreadPool();
            response = Executors.newCachedThreadPool();

            receiverQueue = new LinkedBlockingQueue<>();
            senderQueue = new LinkedBlockingQueue<>();


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
                    Socket socket = serverSocket.accept();
                    Callable receiver = () -> {
                        Ask ask;
                        try {
                            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                            ask = (Ask) is.readObject();
                            Log.logger.trace("Получен запрос от клиента: " + ask.getMessage() + ", \nСтатус: " + ask.getStatus());
                            return ask;
                        } catch (IOException | ClassNotFoundException e) {
                            Log.logger.error("Некорректный запрос от клиента...");
                            ask = new Ask();
                            ask.setStatus(Status.ERROR);
                            return ask;
                        }
                    };
                    FutureTask<Ask> receiverTask = new FutureTask<Ask>(receiver);
                    request.submit(receiverTask);
                    receiverQueue.add(receiverTask.get());
                    Callable request = () -> {
                        Answer answer = new Answer();
                        Ask ask = receiverQueue.poll();
                        if (ask.getStatus().equals(Status.RUNNING)) {
                            String command = ask.getMessage();
                            String[] commandExecuter = command.split("\\s+", 2);
                            commandManager.setUser(ask.getUser());
                            commandManager.setCollectionInfo(clientInfo);
                            commandManager.setNewDragon(ask.getDragon());
                            if (commandExecuter.length == 2) {
                                String clientMessage = commandManager.clientRun(commandExecuter[0], commandExecuter[1], clientCommands);
                                Log.logger.trace("Команда: " + command);
                                answer.setMessage(clientMessage);
                                answer.setStatus(ClientStatus.REGISTER);
                                Log.logger.trace("Ответ обработан.");
                            } else if (commandExecuter.length == 1) {
                                String clientMessage = commandManager.clientRun(commandExecuter[0], "", clientCommands);
                                Log.logger.trace("Команда: " + command);
                                answer.setMessage(clientMessage);
                                answer.setStatus(ClientStatus.REGISTER);
                                Log.logger.trace("Ответ обработан.");
                            }
                        } else {
                            if (ask.getStatus().equals(Status.LOGIN)) {
                                try {
                                    userManager.add(ask.getUser());
                                    answer.setMessage("Регистрация прошла успешно!");
                                    answer.setStatus(ClientStatus.REGISTER);
                                    Log.logger.trace("Ответ обработан.");
                                } catch (DatabaseException e) {
                                    answer.setMessage(e.getMessage());
                                    answer.setStatus(ClientStatus.UNKNOWN);
                                    Log.logger.trace("Ответ обработан.");
                                }
                            } else if (ask.getStatus().equals(Status.ERROR)) {
                                answer.setMessage("Ошибка при обработке команды сервером. Повторите свой запрос снова...");
                            } else {
                                if (userManager.isValid(ask.getUser())) {
                                    answer.setMessage("Авторизация прошла успешно.");
                                    answer.setStatus(ClientStatus.REGISTER);
                                    Log.logger.trace("Ответ обработан.");
                                } else {
                                    answer.setMessage("Неверный логин и пароль. Такого пользователя не существует.");
                                    answer.setStatus(ClientStatus.UNKNOWN);
                                    Log.logger.trace("Ответ обработан.");
                                }
                            }
                        }
                        return answer;
                    };
                    FutureTask<Answer> handlerTask = new FutureTask(request);
                    new Thread(handlerTask).start();
                    senderQueue.add(handlerTask.get());
                    Callable sender = () -> {
                        Answer answer;
                        if (!senderQueue.isEmpty()) {
                            try {
                                answer = senderQueue.poll();
                                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                oos.writeObject(answer);
                                Log.logger.trace("Сообщение успешно отправлено клиенту.");
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.logger.error(e.getMessage());
                            }
                        }
                        return "Введите команду:";
                    };
                    FutureTask<String> senderTask = new FutureTask<>(sender);
                    response.submit(senderTask);
                    System.out.println(senderTask.get());
                }
            } catch (IOException ignored) {
                //
            } catch (InterruptedException e) {
                Log.logger.error("Программист криворукий мудак, почини программу!");
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public HashMap<String, Command> loadServerCommands() {
        HashMap<String, Command> commands = new HashMap<>();
        serverInfo = new HashMap<>();
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