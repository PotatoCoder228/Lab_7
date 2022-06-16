package ru.potatocoder228.itmo.lab7.server;

import ru.potatocoder228.itmo.lab7.commands.Command;
import ru.potatocoder228.itmo.lab7.log.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Scanner;

public class ServerConsole extends Thread{
    HashMap<String, Command> map;
    CommandManager commandManager;
    ServerSocket serverSocket;

    public ServerConsole(HashMap<String, Command> map, CommandManager commandManager) {
        this.map = map;
        this.commandManager = commandManager;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            try {
                System.out.print("Введите команду:");
                String line = scanner.nextLine();
                if (line.equals("exit")) {
                    Log.logger.trace("Завершение работы сервера.");
                    serverSocket.close();
                    System.exit(0);
                } else if (line.equals("save")) {
                    String clientMessage = commandManager.clientRun(line, "", map);
                    Log.logger.trace(clientMessage);
                } else {
                    Log.logger.trace("Некорректная команда.");
                }
            } catch (IOException | NullPointerException e) {
                Log.logger.error(e.getMessage());
            }
        }
    }
    public void setServerSocket(ServerSocket socket){
        this.serverSocket = socket;
    }
}
