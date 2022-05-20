package ru.potatocoder228.itmo.lab6.commands;


import ru.potatocoder228.itmo.lab6.data.CollectionManager;
import ru.potatocoder228.itmo.lab6.data.Dragon;

import java.util.Map;

/**
 * Команда-фильтр, выводящая все объекты, поле description которых больше заданного
 */

public class FilterGreaterDescription implements Command {
    protected String nameOfCommand;
    protected String description;
    protected String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public FilterGreaterDescription(Map<String, String> info, Map<String, Command> map) {
        nameOfCommand = "filter_greater_than_description";
        description = "вывести элементы, значение поля description которых больше заданного.";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }

    @Override
    public String execute(CollectionManager collectionManager) {
        String status = "Вот все нужные объекты:\n";
        for(Dragon dragon: collectionManager.getCollection()){
            if(dragon.getDescription().length() > Integer.parseInt(arg)){
                status += dragon.toString();
            }
        }
        return status;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
    private boolean compareDescription(Dragon dragon){
        if(dragon.getDescription().length() > Integer.parseInt(arg)){
            return true;
        }else{
            return false;
        }
    }
}
