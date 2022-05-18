package ru.potatocoder228.itmo.lab6.commands;


import ru.potatocoder228.itmo.lab6.data.CollectionManager;

public interface Command {
    String execute(CollectionManager collection);

    //String getInformation();
    //AbstractArgument<?>[] getArgs();
    //String getName();
    void setArg(String arg);
}
