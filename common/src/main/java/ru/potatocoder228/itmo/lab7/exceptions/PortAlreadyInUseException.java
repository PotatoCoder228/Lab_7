package ru.potatocoder228.itmo.lab7.exceptions;

public class PortAlreadyInUseException extends ConnectionException {
    public PortAlreadyInUseException() {
        super("Порт уже используется.");
    }
}