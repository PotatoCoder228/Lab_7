package ru.potatocoder228.itmo.lab7.commands;

import ru.potatocoder228.itmo.lab7.data.CollectionManager;

import java.util.Map;

/**
 * Команда добавляющая элемент в коллекцию
 */

public class Add implements Command {

    private String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public Add(Map<String, String> info, Map<String, Command> map) {
        String nameOfCommand = "add";
        String description = "добавление нового элемента в коллекцию.";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }

    @Override
    public synchronized String execute(CollectionManager collectionManager) {
        String status;
        try {
            collectionManager.getDragonManager().add(collectionManager.getNewDragon());
            status = "Команда выполнена.";
        } catch (NullPointerException e) {
            status = "Не удалось добавить элемент в коллекцию...";
        }
        return status;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}

