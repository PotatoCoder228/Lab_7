package ru.potatocoder228.itmo.lab6.exceptions;

public class EmptyStringException extends InvalidDataException {
    public EmptyStringException() {
        super("Строка не может быть пустой");
    }
}