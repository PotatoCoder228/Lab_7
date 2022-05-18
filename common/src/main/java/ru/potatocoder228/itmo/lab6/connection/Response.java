package ru.potatocoder228.itmo.lab6.connection;

import java.io.Serializable;

public interface Response extends Serializable {
    public String getMessage();

    public Status getStatus();
}
