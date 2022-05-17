package ru.potatocoder228.itmo.lab6.server;

import ru.potatocoder228.itmo.lab6.commands.Command;
import ru.potatocoder228.itmo.lab6.connection.AskMsg;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class ServerConsole {
    HashMap<String, Command> map;
    CommandManager commandManager;
    public ServerConsole(HashMap<String, Command> map, CommandManager commandManager){
        this.map = map;
        this.commandManager = commandManager;
    }
    public void parseCommand(){
        try {
            Scanner scanner = new Scanner(System.in);
            if (System.in.available() > 0) {
                String line = scanner.nextLine();
                String[] lines = line.split("\\s+");
                if (lines.length == 2) {
                    String clientMessage = commandManager.run(lines[0], lines[1], map);
                    //System.out.println(clientMessage);
                    System.out.println(clientMessage);
                } else if (lines.length == 1) {
                    String clientMessage = commandManager.run(lines[0], "", map);
                    System.out.println(clientMessage);//TODO
                } else {
                    System.out.println("Мы соснули");
                }
            }
        }catch (IOException e){
            System.out.println("Игнорь в команд манагере");
        }
    }
}
