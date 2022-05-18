package ru.potatocoder228.itmo.lab6.exceptions;

public class InvalidEnumException extends InvalidDataException {
    public InvalidEnumException() {
        super("Неправильная константа.");
    }
}
