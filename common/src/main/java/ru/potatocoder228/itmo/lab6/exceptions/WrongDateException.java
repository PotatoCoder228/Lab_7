package ru.potatocoder228.itmo.lab6.exceptions;

/**
 * Исключение бросается, когда у объекта неверная дата создания
 */

public class WrongDateException extends WrongFieldException {
    public WrongDateException(String s) {
        super(s);
        System.out.println(s);
    }
    public WrongDateException() {
        super();
    }
}