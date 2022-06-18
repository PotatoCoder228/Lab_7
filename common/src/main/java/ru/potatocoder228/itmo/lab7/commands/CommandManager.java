package ru.potatocoder228.itmo.lab7.commands;

import ru.potatocoder228.itmo.lab7.data.CollectionManager;
import ru.potatocoder228.itmo.lab7.data.Dragon;
import ru.potatocoder228.itmo.lab7.user.User;

import java.util.HashMap;

public class CommandManager {
    private final CollectionManager collectionManager;

    public CommandManager(CollectionManager collection) {
        this.collectionManager = collection;
    }


    public synchronized String clientRun(String com, String arg, HashMap<String, Command> map) {
        String clientMessage;
        try {
            Command command = map.get(com);
            command.setArg(arg);
            clientMessage = command.execute(collectionManager);
        } catch (NullPointerException e) {
            e.printStackTrace();
            clientMessage = "Некорректная команда";
        }
        return clientMessage;
    }

    public synchronized void setNewDragon(Dragon dragon) {
        collectionManager.setDragon(dragon);
    }

    public synchronized void setUser(User user) {
        collectionManager.setUser(user);
    }

    public void setCollectionInfo(HashMap<String, String> map) {
        this.collectionManager.setInfo(map);
    }
}
