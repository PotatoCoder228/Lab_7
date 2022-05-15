package ru.potatocoder228.itmo.lab6.exceptions;

public class InvalidDateFormatException extends InvalidDataException {
    public InvalidDateFormatException(){
        super("формат даты должен быть dd-MM-yyyy");
    }
}