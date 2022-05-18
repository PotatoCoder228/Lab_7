package ru.potatocoder228.itmo.lab6;

import ru.potatocoder228.itmo.lab6.connection.AnswerMsg;

import java.util.Scanner;

public class ClientConsole {
    public boolean scriptMode;
    public ClientConsole(boolean mode){
        this.scriptMode = mode;
    }
    public AnswerMsg inputCommand() {
        System.out.printf("Введите команду:");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine().toLowerCase();
        AnswerMsg msg = new AnswerMsg();
        if (command.equals("exit")) {
            System.out.println("Завершение работы приложения...");
            System.exit(0);
        }
        String[] lines = command.split("\\s+");
        if (lines.length == 2 && !lines[0].substring(0, 6).equals("update")) {
            msg.setMessage(command);
        } else if (lines.length == 1 && !lines[0].equals("add") && !lines[0].equals("add_if_max") && !lines[0].equals("remove_greater")) {
            msg.setMessage(command);
        }
        return msg;
    }
}
