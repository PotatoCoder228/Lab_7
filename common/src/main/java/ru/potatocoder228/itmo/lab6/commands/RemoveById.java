package ru.potatocoder228.itmo.lab6.commands;


import ru.potatocoder228.itmo.lab6.data.CollectionManager;

import java.util.Map;

/**
 * Команда, удаляющая объект по заданному id
 */

public class RemoveById implements Command {
    protected String nameOfCommand;
    protected String description;
    protected String arg;
    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public RemoveById(Map<String, String> info, Map<String, Command> map) {
        nameOfCommand = "remove_by_id";
        description = "удалить элемент из коллекции по его id.";
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
