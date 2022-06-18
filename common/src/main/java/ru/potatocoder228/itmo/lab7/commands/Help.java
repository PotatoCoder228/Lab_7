package ru.potatocoder228.itmo.lab7.commands;


import ru.potatocoder228.itmo.lab7.data.CollectionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Команда, выводящая информацию о командах
 */

public class Help implements Command {
    private String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public Help(Map<String, String> info, Map<String, Command> map) {
        String nameOfCommand = "help";
        String description = "вывод справки по доступным командам.";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }


    @Override
    public synchronized String execute(CollectionManager collectionManager) {
        String status;
        HashMap<String, String> map = collectionManager.getInfo();
        status = map.entrySet().toString().replace(",", "\n");
        return " " + status.replace("=", ":").substring(1, status.length() - 1);
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
