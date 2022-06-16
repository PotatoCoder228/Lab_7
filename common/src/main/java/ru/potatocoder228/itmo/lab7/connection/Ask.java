package ru.potatocoder228.itmo.lab7.connection;

import ru.potatocoder228.itmo.lab7.data.Dragon;

public class Ask implements Request {
    private static final long serialVersionUID = 666;
    private String msg;
    private Dragon dragon;

    public Ask() {
        msg = "";
    }

    public Ask clear() {
        msg = "";
        return this;
    }

    public Ask info(Object str) {
        msg = str.toString();
        return this;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public Dragon getDragon() {
        return this.dragon;
    }

    public void setDragon(Dragon dragon) {
        this.dragon = dragon;
    }
}
