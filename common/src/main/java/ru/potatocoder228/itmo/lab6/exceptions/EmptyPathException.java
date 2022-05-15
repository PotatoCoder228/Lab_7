package ru.potatocoder228.itmo.lab6.exceptions;

public class EmptyPathException extends FileException{
    public EmptyPathException(){
        super("Путь отсутствует.");
    }
}
