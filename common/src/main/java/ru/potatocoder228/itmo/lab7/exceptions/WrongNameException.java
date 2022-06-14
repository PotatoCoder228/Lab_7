package ru.potatocoder228.itmo.lab7.exceptions;

/**
 * Исключение бросается, когда у объекта неверное имя
 */

public class WrongNameException extends WrongFieldException {
    public WrongNameException(String s) {
        super(s);
        System.out.println(s);
    }

    public WrongNameException() {
        super();
    }
}
