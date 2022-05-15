package ru.potatocoder228.itmo.lab6.exceptions;

public class ExitException extends CommandException{
    public ExitException(){
        super("Завершение работы программы...");
    }
}
