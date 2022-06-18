package ru.potatocoder228.itmo.lab7.commands;


import ru.potatocoder228.itmo.lab7.data.CollectionManager;
import ru.potatocoder228.itmo.lab7.data.Dragon;

import java.util.Map;

/**
 * Команда, выводящая все элементы коллекции в строковом представлении
 */

public class Show implements Command {
    private String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public Show(Map<String, String> info, Map<String, Command> map) {
        String nameOfCommand = "show";
        String description = "вывод элементов коллекции в строковом представлении.";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }


    @Override
    public String execute(CollectionManager collectionManager) {
        String status = "Вот все объекты коллекции:\n";
        StringBuilder string = new StringBuilder();
        for (Dragon dragon : collectionManager.getCollection()) {
            string.append(dragon.toString());
        }
        status += string.toString();
        if (collectionManager.getCollection().isEmpty()) {
            status = "Коллекция пуста.";
        }
        return status;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
