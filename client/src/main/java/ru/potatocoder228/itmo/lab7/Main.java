package ru.potatocoder228.itmo.lab7;

public class Main {
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в клиентскую версию консольного приложения.");
        Client client = new Client();
        client.start(args);
    }
}
