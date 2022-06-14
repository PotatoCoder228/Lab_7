package ru.potatocoder228.itmo.lab7.exceptions;

public class NoSuchCommandException extends CommandException {
    public NoSuchCommandException() {
        super("Неправильная команда.");
    }
}