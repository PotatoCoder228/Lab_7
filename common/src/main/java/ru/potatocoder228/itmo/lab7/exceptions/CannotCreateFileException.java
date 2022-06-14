package ru.potatocoder228.itmo.lab7.exceptions;

public class CannotCreateFileException extends FileException {
    public CannotCreateFileException(String msg) {
        super(msg);
    }
}
