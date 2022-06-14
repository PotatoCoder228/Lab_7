package ru.potatocoder228.itmo.lab7.connection;

import java.io.Serializable;

public class AskMsg implements Serializable {
    private static final long serialVersionUID = 667;
    String msg;

    public AskMsg clear() {
        msg = "";
        return this;
    }

    public AskMsg info(Object str) {
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
