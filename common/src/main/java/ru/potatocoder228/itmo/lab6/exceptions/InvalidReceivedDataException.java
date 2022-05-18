package ru.potatocoder228.itmo.lab6.exceptions;

public class InvalidReceivedDataException extends InvalidDataException {
    public InvalidReceivedDataException() {
        super("Полученные данные повреждены.");
    }
}
