package ru.potatocoder228.itmo.lab6.exceptions;

public class EmptyCollectionException extends CommandException{
    public EmptyCollectionException(){
        super("Коллекция пуста.");
    }
}