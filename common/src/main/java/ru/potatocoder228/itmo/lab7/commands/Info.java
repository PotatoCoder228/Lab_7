package ru.potatocoder228.itmo.lab7.commands;

import ru.potatocoder228.itmo.lab7.data.CollectionManager;

import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Команда, выводящая информацию о коллекции
 */

public class Info implements Command {
    private String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public Info(Map<String, String> info, Map<String, Command> map) {
        String nameOfCommand = "info";
        String description = "выводит информацию о коллекции.";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }

    @Override
    public synchronized String execute(CollectionManager collectionManager) {
        String status = "\nИнформация о коллекции:" + "\n\tТип коллекции: LinkedList" + "\n\tВремя создания коллекции: ";
        status += collectionManager.getCreatingTime().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"));
        status += "\n\tКоличество элементов в коллекции: " + collectionManager.getSize();
        return status;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
