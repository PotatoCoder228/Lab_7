package ru.potatocoder228.itmo.lab6.exceptions;

public class InvalidNumberException extends InvalidDataException {
    public InvalidNumberException(){
        super("Некорректный формат числа.");
    }
    public InvalidNumberException(String msg){
        super(msg);
    }
}