package ru.potatocoder228.itmo.lab6.exceptions;

public class CommandException extends RuntimeException{
    public CommandException(String message) {
        super(message);
    }
}
