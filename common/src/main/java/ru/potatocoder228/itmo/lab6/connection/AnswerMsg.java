package ru.potatocoder228.itmo.lab6.connection;

import ru.potatocoder228.itmo.lab6.data.Dragon;

public class AnswerMsg implements Response{
    private static final long serialVersionUID = 666;
    private String msg = null;
    private Status status;
    private Dragon dragon;

    public AnswerMsg() {
        msg = "";
        status = Status.FINE;
    }

    public AnswerMsg clear() {
        msg = "";
        return this;
    }

    public AnswerMsg info(Object str) {
        msg = str.toString();// + "\n";
        return this;
    }

    public AnswerMsg error(Object str) {
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

    public AnswerMsg setStatus(Status st) {
        status = st;
        return this;
    }

    @Override
    public String toString() {
        if (getStatus() == Status.ERROR) {
            return "Err: " + getMessage();
        }
        return getMessage();
    }
    public void setDragon(Dragon dragon){
        this.dragon = dragon;
    }
    public Dragon getDragon(){
        return this.dragon;
    }
}
