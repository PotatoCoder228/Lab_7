package ru.potatocoder228.itmo.lab6.connection;

import java.io.Serializable;

public class AskMsg implements Serializable {
    String msg;
    private static final long serialVersionUID = 666;
    private Status status;
    public void setMessage(String message){
        this.msg = message;
    }
    public AskMsg clear(){
        msg = "";
        return this;
    }
    public AskMsg info(Object str){
        msg = str.toString();// + "\n";
        return this;
    }
    public AskMsg error(Object str){
        msg = "Error: " + str.toString();
        setStatus(Status.ERROR);
        return this;
    }
    public AskMsg setStatus(Status st){
        status = st;
        return this;
    }
    public String getMessage(){
        return msg;
    }
    public Status getStatus(){
        return status;
    }
}
