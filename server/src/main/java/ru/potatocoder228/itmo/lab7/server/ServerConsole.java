package ru.potatocoder228.itmo.lab7.server;

import ru.potatocoder228.itmo.lab7.commands.Command;
import ru.potatocoder228.itmo.lab7.commands.CommandManager;
import ru.potatocoder228.itmo.lab7.database.DatabaseHandler;
import ru.potatocoder228.itmo.lab7.log.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerConsole extends Thread {
    private HashMap<String, Command> map;
    private CommandManager commandManager;
    private ServerSocket serverSocket;
    private DatabaseHandler databaseHandler;

    public ServerConsole(HashMap<String, Command> map, CommandManager commandManager, DatabaseHandler databaseHandler) {
        this.map = map;
        this.commandManager = commandManager;
        this.databaseHandler = databaseHandler;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Введите команду:\n");
                String line = scanner.nextLine();
                if (line.equals("exit")) {
                    Log.logger.trace("Завершение работы сервера.");
                    serverSocket.close();
                    databaseHandler.closeConnection();
                    System.exit(0);
                } else {
                    Log.logger.trace("Некорректная команда.");
                }
            } catch (NoSuchElementException e) {
                Thread.currentThread().interrupt();
                run();
            } catch (IOException | NullPointerException e) {
                Log.logger.error(e.getMessage());
            }
        }
    }

    public void setServerSocket(ServerSocket socket) {
        this.serverSocket = socket;
    }
}
