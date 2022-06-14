package ru.potatocoder228.itmo.lab7.exceptions;

public class FileException extends Exception {
    public FileException(String msg) {
        super(msg);
        System.out.println(msg);
    }
}
