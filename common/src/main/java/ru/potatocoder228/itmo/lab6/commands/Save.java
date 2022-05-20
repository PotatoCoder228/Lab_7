package ru.potatocoder228.itmo.lab6.commands;

import ru.potatocoder228.itmo.lab6.data.CollectionManager;

import java.util.Map;

/**
 * Команда, сохраняющая коллекцию в файл
 */

public class Save implements Command {
    protected String nameOfCommand;
    protected String description;
    protected String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public Save(Map<String, String> info, Map<String, Command> map) {
        nameOfCommand = "save";
        description = "сохранить коллекцию в файл(только для сервера.)";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }


    @Override
    public String execute(CollectionManager collectionManager) {
        collectionManager.saveCollection();
        return "Коллекция успешно сохранена.";
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
