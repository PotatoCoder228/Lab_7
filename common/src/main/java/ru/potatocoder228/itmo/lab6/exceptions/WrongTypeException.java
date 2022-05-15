package ru.potatocoder228.itmo.lab6.exceptions;

/**
 * Исключение бросается, когда у объекта неверный тип
 */

public class WrongTypeException extends WrongFieldException {
    public WrongTypeException(String s) {
        super(s);
        System.out.println(s);
    }
    public WrongTypeException() {
        super();
    }
}
