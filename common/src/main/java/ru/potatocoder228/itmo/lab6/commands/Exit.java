package ru.potatocoder228.itmo.lab6.commands;


import ru.potatocoder228.itmo.lab6.data.CollectionManager;

import java.util.Map;

/**
 * Команда, прекращающая выполнение программы(а зачем ты вообще нужен?)
 */

public class Exit implements Command {
    protected String nameOfCommand;
    protected String description;
    protected String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь",возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public Exit(Map<String, String> info, Map<String, Command> map) {
        nameOfCommand = "exit";
        description = "завершить программу(без сохранения в файл).";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }

    @Override
    public String execute(CollectionManager collectionManager) {
        System.out.println("Завершение работы сервера...");
        System.exit(0);
        return nameOfCommand;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
