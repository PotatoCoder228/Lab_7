package ru.potatocoder228.itmo.lab7.commands;


import ru.potatocoder228.itmo.lab7.data.CollectionManager;
import ru.potatocoder228.itmo.lab7.data.Dragon;

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
    public synchronized String execute(CollectionManager collectionManager) {
        String status = "Вот все нужные объекты:\n";
        StringBuilder string = new StringBuilder();
        for (Dragon dragon : collectionManager.getCollection()) {
            if (compareCave(dragon)) {
                string.append(dragon.toString());
            }
        }
        status += string.toString();
        return status;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    private boolean compareCave(Dragon dragon) {
        return dragon.getCave() < Integer.parseInt(arg);
    }
}
