package ru.potatocoder228.itmo.lab7.connection;

import java.io.Serializable;

public class Answer implements Serializable {
    private static final long serialVersionUID = 667;
    String msg;

    public Answer clear() {
        msg = "";
        return this;
    }

    public Answer info(Object str) {
        msg = str.toString();
        return this;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }
}
