package ru.potatocoder228.itmo.lab6.exceptions;

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
