package ru.potatocoder228.itmo.lab7.commands;


import ru.potatocoder228.itmo.lab7.data.CollectionManager;

import java.util.Map;

/**
 * Команда очищающая коллекцию
 */

public class Clear implements Command {
    protected String nameOfCommand;
    protected String description;
    protected String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public Clear(Map<String, String> info, Map<String, Command> map) {
        nameOfCommand = "clear";
        description = "очистить коллекцию.";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }


    @Override
    public synchronized String execute(CollectionManager collectionManager) {
        collectionManager.clear();
        String status = "Коллекция очищена.";
        return status;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
