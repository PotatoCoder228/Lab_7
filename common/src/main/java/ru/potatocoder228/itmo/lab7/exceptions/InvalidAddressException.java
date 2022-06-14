package ru.potatocoder228.itmo.lab7.exceptions;

public class InvalidAddressException extends ConnectionException {
    public InvalidAddressException(String s) {
        super(s);
    }
}