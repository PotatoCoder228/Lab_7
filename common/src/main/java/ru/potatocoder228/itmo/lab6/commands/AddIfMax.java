package ru.potatocoder228.itmo.lab6.commands;


import ru.potatocoder228.itmo.lab6.data.CollectionManager;

import java.util.Map;

/**
 * Команда добавляющая элемент в коллекцию, если он больше максимального
 */

public class AddIfMax implements Command {
    protected String nameOfCommand;
    protected String description;
    protected String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public AddIfMax(Map<String, String> info, Map<String, Command> map) {
        nameOfCommand = "add_if_max";
        description = "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }

    @Override
    public String execute(CollectionManager collectionManager) {
        String status = "";
        collectionManager.getNewDragon().setCreationDate();
        collectionManager.getNewDragon().setId();
        int count = (int) collectionManager.getCollection()
                .stream()
                .filter(w -> w.getId() == collectionManager.getNewDragon().getId()).count();

        if (count == 0 && collectionManager.getCollection().getLast().getAge() < collectionManager.getNewDragon().getAge()) {
            collectionManager.addLast(collectionManager.getNewDragon());
            status = "Объект успешно добавлен в коллекцию.";
        } else if (count > 0) {
            status = "Объект с таким id уже есть в коллекции.";
        } else {
            status = "Объект меньше максимального.";
        }
        return status;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
