package ru.potatocoder228.itmo.lab7.exceptions;

public class InvalidReceivedDataException extends InvalidDataException {
    public InvalidReceivedDataException() {
        super("Полученные данные повреждены.");
    }
}
