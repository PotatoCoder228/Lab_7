package ru.potatocoder228.itmo.lab7.commands;


import ru.potatocoder228.itmo.lab7.data.CollectionManager;

import java.util.Map;

/**
 * Команда, удаляющая первый элемент в коллекции
 */

public class RemoveFirst implements Command {
    protected String nameOfCommand;
    protected String description;
    protected String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public RemoveFirst(Map<String, String> info, Map<String, Command> map) {
        nameOfCommand = "remove_first";
        description = "удалить первый элемент из коллекции.";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }

    @Override
    public synchronized String execute(CollectionManager collectionManager) {
        String status;
        if (collectionManager.getSize() > 0) {
            collectionManager.getCollection().removeFirst();
            status = "Первый элемент коллекции успешно удалён.";
        } else {
            status = "Коллекция пуста.";
        }
        return status;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
