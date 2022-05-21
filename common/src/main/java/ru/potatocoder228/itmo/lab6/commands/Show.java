package ru.potatocoder228.itmo.lab6.commands;


import ru.potatocoder228.itmo.lab6.data.CollectionManager;
import ru.potatocoder228.itmo.lab6.data.Dragon;

import java.util.Map;

/**
 * Команда, выводящая все элементы коллекции в строковом представлении
 */

public class Show implements Command {
    protected String nameOfCommand;
    protected String description;
    protected String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public Show(Map<String, String> info, Map<String, Command> map) {
        nameOfCommand = "show";
        description = "вывод элементов коллекции в строковом представлении.";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }


    @Override
    public String execute(CollectionManager collectionManager) {
        String status = "Вот все объекты коллекции:\n";
        for (Dragon dragon : collectionManager.getCollection()) {
            status += dragon.toString();
        }
        return status;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
