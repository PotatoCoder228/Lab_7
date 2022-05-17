package ru.potatocoder228.itmo.lab6.commands;


import ru.potatocoder228.itmo.lab6.data.CollectionManager;

import java.util.Map;

/**
 * Команда-фильтр, выводящая все объекты, поле cave которых меньше заданного
 */

public class FilterLessCave implements Command {
    protected String nameOfCommand;
    protected String description;
    protected String arg;
    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public FilterLessCave(Map<String, String> info, Map<String, Command> map) {
        nameOfCommand = "filter_less_than_cave";
        description = "вывести все элементы, значение поля cave которых меньше заданного.";
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
