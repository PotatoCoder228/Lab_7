package ru.potatocoder228.itmo.lab7.commands;

import ru.potatocoder228.itmo.lab7.data.CollectionManager;

import java.util.Map;

/**
 * Команда, выполняющая скрипт
 */

public class ExecuteScript implements Command {
    private String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command'а
     */

    public ExecuteScript(Map<String, String> info, Map<String, Command> map) {
        String nameOfCommand = "execute_script";
        String description = "считать и исполнить скрипт из указанного файла.";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }


    @Override
    public synchronized String execute(CollectionManager collectionManager) {
        return "Зарезервированная команда.";
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
