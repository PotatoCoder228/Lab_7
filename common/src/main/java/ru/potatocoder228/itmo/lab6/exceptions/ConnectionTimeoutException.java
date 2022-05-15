package ru.potatocoder228.itmo.lab6.exceptions;

public class ConnectionTimeoutException extends ConnectionException{
    public ConnectionTimeoutException(){
        super("Время ответа истекло");
    }
}