package ru.potatocoder228.itmo.lab6.exceptions;

public class CannotCreateFileException extends FileException{
    public CannotCreateFileException(){
        super("Невозможно создать файл.");
    }
}
