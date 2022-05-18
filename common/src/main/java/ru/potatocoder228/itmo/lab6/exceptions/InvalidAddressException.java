package ru.potatocoder228.itmo.lab6.exceptions;

public class InvalidAddressException extends ConnectionException {
    public InvalidAddressException() {
        super("Некорректный адрес.");
    }

    public InvalidAddressException(String s) {
        super(s);
    }
}