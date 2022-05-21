package ru.potatocoder228.itmo.lab6.server;

import ru.potatocoder228.itmo.lab6.commands.Command;
import ru.potatocoder228.itmo.lab6.log.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Scanner;

public class ServerConsole {
    HashMap<String, Command> map;
    CommandManager commandManager;

    public ServerConsole(HashMap<String, Command> map, CommandManager commandManager) {
        this.map = map;
        this.commandManager = commandManager;
    }

    public void parseCommand(ServerSocket socket) {
        try {
            Scanner scanner = new Scanner(System.in);
            if (System.in.available() > 0) {
                String line = scanner.nextLine().toLowerCase();
                if (line.equals("exit")) {
                    //System.out.println("Завершение работы сервера...");
                    Log.logger.trace("Завершение работы сервера.");
                    socket.close();
                    System.exit(0);
                }
                String[] lines = line.split("\\s+", 2);
                if (lines.length == 2) {
                    String clientMessage = commandManager.clientRun(lines[0], lines[1], map);
                    System.out.println(clientMessage);
                } else if (lines.length == 1) {
                    String clientMessage = commandManager.clientRun(lines[0], "", map);
                    System.out.println(clientMessage);//TODO
                } else {
                    System.out.println("Некорректная команда.");
                }
                System.out.print("Введите команду:");
            }
        } catch (IOException | NullPointerException e) {
            Log.logger.error(e.getMessage());
        }
    }
}
