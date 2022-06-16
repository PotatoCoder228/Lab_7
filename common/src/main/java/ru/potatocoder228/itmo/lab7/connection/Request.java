package ru.potatocoder228.itmo.lab7.connection;

import java.io.Serializable;

public interface Request extends Serializable {
    String getMessage();
}
