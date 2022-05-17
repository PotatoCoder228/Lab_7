package ru.potatocoder228.itmo.lab6.commands;

import ru.potatocoder228.itmo.lab6.data.CollectionManager;

import java.util.Map;

/**
 * Команда добавляющая элемент в коллекцию
 */

public class Add implements Command {

    protected String nameOfCommand;
    protected String description;
    protected String arg;
    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public Add(Map<String, String> info, Map<String, Command> map) {
        nameOfCommand = "add";
        description = "добавление нового элемента в коллекцию.";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }
    @Override
    public String execute(CollectionManager collectionManager) {
        //
        return nameOfCommand;
    }
    public void setArg(String arg){
        this.arg = arg;
    }
}

