package ru.potatocoder228.itmo.lab7.commands;


import ru.potatocoder228.itmo.lab7.data.CollectionManager;

import java.util.Map;

/**
 * Команда, удаляющая объект по заданному id
 */

public class RemoveById implements Command {
    private String arg;

    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public RemoveById(Map<String, String> info, Map<String, Command> map) {
        String nameOfCommand = "remove_by_id";
        String description = "удалить элемент из коллекции по его id.";
        info.put(nameOfCommand, description);
        map.put(nameOfCommand, this);
    }

    @Override
    public synchronized String execute(CollectionManager collectionManager) {
        String status;
        try {
            collectionManager.getDragonManager().removeByID(Integer.parseInt(arg), collectionManager);
            boolean exists = collectionManager.getCollection().stream()
                    .anyMatch(w -> compareId(w.getId()));
            if (exists) {
                status = "Объект успешно удалён из коллекции";
            } else {
                status = "Объекта с таким id не существует...";
            }
        } catch (NumberFormatException e) {
            status = "Некорректный аргумент команды.";
        }
        return status;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public boolean compareId(int w) {
        return w == Integer.parseInt(arg);
    }
}
