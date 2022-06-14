package ru.potatocoder228.itmo.lab7.server;

import ru.potatocoder228.itmo.lab7.commands.Command;
import ru.potatocoder228.itmo.lab7.data.CollectionManager;
import ru.potatocoder228.itmo.lab7.data.Dragon;

import java.util.HashMap;

public class CommandManager {
    private String clientMessage;
    private CollectionManager collectionManager;
    private HashMap<String, Command> map;

    public CommandManager(CollectionManager collection) {
        this.collectionManager = collection;
    }

    public void setMap(HashMap<String, Command> map) {
        this.map = map;
    }

    public String clientRun(String com, String arg, HashMap<String, Command> map) {
        Command command = map.get(com);
        command.setArg(arg);
        clientMessage = command.execute(collectionManager);
        return clientMessage;
    }

    public void setNewDragon(Dragon dragon) {
        collectionManager.setDragon(dragon);
    }

    public void setCollectionInfo(HashMap<String, String> map) {
        this.collectionManager.setInfo(map);
    }
}
