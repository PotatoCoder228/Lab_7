package ru.potatocoder228.itmo.lab6.commands;

import ru.potatocoder228.itmo.lab6.data.CollectionManager;

import java.util.Map;

/**
 * Команда, выполняющая скрипт
 */

public class ExecuteScript implements Command {
    protected String nameOfCommand;
    protected String description;
    protected String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public ExecuteScript(Map<String, String> info, Map<String, Command> map) {
        nameOfCommand = "execute_script";
        description = "считать и исполнить скрипт из указанного файла.";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }


    @Override
    public String execute(CollectionManager collectionManager) {
        //
        return nameOfCommand;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
