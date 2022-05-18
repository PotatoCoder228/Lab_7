package ru.potatocoder228.itmo.lab6.exceptions;

public class NoSuchCommandException extends CommandException {
    public NoSuchCommandException() {
        super("Неправильная команда.");
    }
}