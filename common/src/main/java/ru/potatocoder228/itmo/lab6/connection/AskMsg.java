package ru.potatocoder228.itmo.lab6.connection;

import java.io.Serializable;

public class AskMsg implements Serializable {
    private static final long serialVersionUID = 667;
    String msg;
    private Status status;

    public AskMsg clear() {
        msg = "";
        return this;
    }

    public AskMsg info(Object str) {
        msg = str.toString();// + "\n";
        return this;
    }

    public AskMsg error(Object str) {
        msg = "Error: " + str.toString();
        setStatus(Status.ERROR);
        return this;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public Status getStatus() {
        return status;
    }

    public AskMsg setStatus(Status st) {
        status = st;
        return this;
    }
}
