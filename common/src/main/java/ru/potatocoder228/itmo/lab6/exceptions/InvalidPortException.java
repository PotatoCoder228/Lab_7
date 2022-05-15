package ru.potatocoder228.itmo.lab6.exceptions;

public class InvalidPortException extends ConnectionException {
    public InvalidPortException(){
        super("Некорректный порт");
    }
}
