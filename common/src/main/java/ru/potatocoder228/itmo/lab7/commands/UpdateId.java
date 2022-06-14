package ru.potatocoder228.itmo.lab7.commands;


import ru.potatocoder228.itmo.lab7.data.CollectionManager;

import java.util.Map;

/**
 * Команда, обновляющая элемент коллекции с заданным id
 */

public class UpdateId implements Command {
    protected String nameOfCommand;
    protected String description;
    protected String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public UpdateId(Map<String, String> info, Map<String, Command> map) {
        nameOfCommand = "update";
        description = "обновить значение элемента коллекции, id которого равен заданному.";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }


    @Override
    public synchronized String execute(CollectionManager collectionManager) {
        String status;
        collectionManager.getCollection().removeIf(dragon -> dragon.getId() == Integer.parseInt(arg));
        collectionManager.getNewDragon().setId();
        collectionManager.getNewDragon().setCreationDate();
        collectionManager.getCollection().add(collectionManager.getNewDragon());
        status = "Объект успешно обновлён.";
        return status;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
