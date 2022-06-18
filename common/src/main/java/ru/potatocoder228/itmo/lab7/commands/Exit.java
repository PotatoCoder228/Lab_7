package ru.potatocoder228.itmo.lab7.commands;


import ru.potatocoder228.itmo.lab7.data.CollectionManager;

import java.util.Map;

/**
 * Команда, прекращающая выполнение программы(а зачем ты вообще нужен?)
 */

public class Exit implements Command {
    private String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command'а
     */

    public Exit(Map<String, String> info, Map<String, Command> map) {
        String nameOfCommand = "exit";
        String description = "завершить программу(без сохранения в файл).";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }

    @Override
    public synchronized String execute(CollectionManager collectionManager) {
        String status = "Завершение работы сервера...";
        System.out.println(status);
        System.exit(0);
        return status;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
