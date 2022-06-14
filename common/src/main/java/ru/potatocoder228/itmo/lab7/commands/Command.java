package ru.potatocoder228.itmo.lab7.commands;


import ru.potatocoder228.itmo.lab7.data.CollectionManager;

public interface Command {
    String execute(CollectionManager collection);

    void setArg(String arg);
}
