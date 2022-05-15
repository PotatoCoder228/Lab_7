package ru.potatocoder228.itmo.lab6.exceptions;

public class MissedCommandArgumentException extends InvalidCommandArgumentException{
    public MissedCommandArgumentException(){
        super("Отсутствует аргумент команды.");
    }
}