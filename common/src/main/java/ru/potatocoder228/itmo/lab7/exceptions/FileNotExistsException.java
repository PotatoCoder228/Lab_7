package ru.potatocoder228.itmo.lab7.exceptions;

public class FileNotExistsException extends FileException {
    public FileNotExistsException(String msg) {
        super(msg);
    }
}
