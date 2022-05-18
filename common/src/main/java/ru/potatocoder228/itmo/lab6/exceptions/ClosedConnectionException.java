package ru.potatocoder228.itmo.lab6.exceptions;

public class ClosedConnectionException extends ConnectionException {
    public ClosedConnectionException() {
        super("Серверный канал закрыт");
    }
}
