package ru.potatocoder228.itmo.lab6;

public class Main {
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в клиентскую версию консольного приложения.");
        try {
            int port = Integer.parseInt(args[0]);
            Client client = new Client(port);
            client.run();
        } catch (NumberFormatException e) {
            System.out.println("Порт должен быть числом. Работа приложения будет завершена.");
            System.out.println("Завершение работы...");
            System.exit(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Вы не ввели порт. Работа приложения будет завершена.");
            System.out.println("Завершение работы...");
            System.exit(0);
        }
    }
}
