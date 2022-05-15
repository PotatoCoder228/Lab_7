package ru.potatocoder228.itmo.lab6.connection;

import ru.potatocoder228.itmo.lab6.data.Dragon;

import java.io.Serializable;

public interface Request extends Serializable {
    public String getStringArg();
    public Dragon getDragon();
    public String getCommandName();
}
