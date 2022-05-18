package ru.potatocoder228.itmo.lab6.server;

import ru.potatocoder228.itmo.lab6.commands.Command;
import ru.potatocoder228.itmo.lab6.data.CollectionManager;

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

    public String run(String com, String arg, HashMap<String, Command> map) {
        Command command = map.get(com);
        command.setArg(arg);
        clientMessage = command.execute(collectionManager);
        return clientMessage;
    }
}
