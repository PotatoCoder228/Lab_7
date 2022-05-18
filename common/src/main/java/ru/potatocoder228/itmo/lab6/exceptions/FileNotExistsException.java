package ru.potatocoder228.itmo.lab6.exceptions;

public class FileNotExistsException extends FileException {
    public FileNotExistsException() {
        super("Файл не существует или него недостаточно прав.");
    }
}
