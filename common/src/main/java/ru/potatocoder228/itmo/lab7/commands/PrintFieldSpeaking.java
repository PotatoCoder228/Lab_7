package ru.potatocoder228.itmo.lab7.commands;


import ru.potatocoder228.itmo.lab7.data.CollectionManager;
import ru.potatocoder228.itmo.lab7.data.Dragon;

import java.util.Collections;
import java.util.Map;

/**
 * Команда, выводящая поля speaking в порядке убывания
 */

public class PrintFieldSpeaking implements Command {
    protected String nameOfCommand;
    protected String description;
    protected String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public PrintFieldSpeaking(Map<String, String> info, Map<String, Command> map) {
        nameOfCommand = "print_field_descending_speaking";
        description = "вывести значения поля speaking всех элементов в порядке убывания.";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }

    @Override
    public synchronized String execute(CollectionManager collectionManager) {
        String status = "";
        StringBuilder string = new StringBuilder();
        collectionManager.getCollection().sort(Collections.reverseOrder());
        for (Dragon dragon : collectionManager.getCollection()) {
            string.append("\n").append(dragon.getSpeaking());
        }
        status += string.toString();
        return status;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
