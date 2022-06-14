package ru.potatocoder228.itmo.lab7.commands;

import ru.potatocoder228.itmo.lab7.data.CollectionManager;

import java.util.Map;

/**
 * Команда добавляющая элемент в коллекцию
 */

public class Add implements Command {

    protected String nameOfCommand;
    protected String description;
    protected String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public Add(Map<String, String> info, Map<String, Command> map) {
        nameOfCommand = "add";
        description = "добавление нового элемента в коллекцию.";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }

    @Override
    public synchronized String execute(CollectionManager collectionManager) {
        String status;
        collectionManager.getNewDragon().setCreationDate();
        collectionManager.getNewDragon().setId();
        int count = (int) collectionManager.getCollection()
                .stream()
                .filter(w -> w.getId() == collectionManager.getNewDragon().getId())
                .count();
        if (count == 0) {
            collectionManager.addLast(collectionManager.getNewDragon());
            status = "Объект успешно добавлен в коллекцию.";
        } else {
            status = "Объект с таким id уже есть в коллекции.";
        }
        return status;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}

