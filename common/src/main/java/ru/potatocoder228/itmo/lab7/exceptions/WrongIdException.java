package ru.potatocoder228.itmo.lab7.exceptions;

/**
 * Исключение бросается, когда у объекта неверный id
 */

public class WrongIdException extends WrongFieldException {
    public WrongIdException(String s) {
        super(s);
        System.out.println(s);
    }

    public WrongIdException() {
        super();
    }
}
