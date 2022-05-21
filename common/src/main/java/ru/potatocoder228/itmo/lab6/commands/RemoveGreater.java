package ru.potatocoder228.itmo.lab6.commands;


import ru.potatocoder228.itmo.lab6.data.CollectionManager;

import java.util.Map;

/**
 * Команда, удаляющая первый элемент в коллекции
 */

public class RemoveGreater implements Command {
    protected String nameOfCommand;
    protected String description;
    protected String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public RemoveGreater(Map<String, String> info, Map<String, Command> map) {
        nameOfCommand = "remove_greater";
        description = "удалить из коллекции все элементы, превышающие заданный.";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }


    @Override
    public String execute(CollectionManager collectionManager) {
        String status = "";
        collectionManager.getNewDragon().setCreationDate();
        collectionManager.getNewDragon().setId();
        collectionManager.getCollection().stream().filter(w -> w.getAge() > collectionManager.getNewDragon().getAge()).forEach(w -> collectionManager.getCollection().remove(w));
        return status;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
