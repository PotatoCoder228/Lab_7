package ru.potatocoder228.itmo.lab6.commands;

import ru.potatocoder228.itmo.lab6.data.CollectionManager;

import java.util.Map;

/**
 * Команда, выводящая информацию о коллекции
 */

public class Info implements Command {
    protected String nameOfCommand;
    protected String description;
    protected String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public Info(Map<String, String> info, Map<String, Command> map) {
        nameOfCommand = "info";
        description = "выводит информацию о коллекции.";
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
