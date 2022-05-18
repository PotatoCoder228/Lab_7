package ru.potatocoder228.itmo.lab6.commands;


import ru.potatocoder228.itmo.lab6.data.CollectionManager;

import java.util.Map;

/**
 * Команда, обновляющая элемент коллекции с заданным id
 */

public class UpdateId implements Command {
    protected String nameOfCommand;
    protected String description;
    protected String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public UpdateId(Map<String, String> info, Map<String, Command> map) {
        nameOfCommand = "update";
        description = "обновить значение элемента коллекции, id которого равен заданному.";
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
