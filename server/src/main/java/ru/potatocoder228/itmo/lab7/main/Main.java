package ru.potatocoder228.itmo.lab7.main;

import ru.potatocoder228.itmo.lab7.log.Log;
import ru.potatocoder228.itmo.lab7.server.Server;


public class Main {
    public static void main(String[] args) {
        System.out.println("Начало работы сервера.");
        int port;
        StringBuilder path = new StringBuilder();
        try {
            port = Integer.parseInt(args[0]);
            for (int i = 1; i < args.length; i++) {
                if (i == 1) {
                    path.append(args[i]);
                } else {
                    path.append(" ").append(args[i]);
                }
            }
            Server server = new Server(port, path.toString());
            server.run();
        } catch (NumberFormatException e) {
            Log.logger.error("Порт должен быть числом. Сервер завершает свою работу.");
            Thread.currentThread().interrupt();
        } catch (IndexOutOfBoundsException e) {
            Log.logger.error("Вы не ввели порт. Сервер завершает свою работу.");
            Thread.currentThread().interrupt();
        } catch (NullPointerException e) {
            Log.logger.error("Вы не ввели путь к переменной окружения. Сервер завершает свою работу.");
            Thread.currentThread().interrupt();
        } catch (IllegalArgumentException e) {
            Log.logger.error("Некорректное значение порта. Работа сервера будет завершена...");
            Thread.currentThread().interrupt();
        }
    }
}
