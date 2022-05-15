package ru.potatocoder228.itmo.lab6.connection;

public class AnswerMsg implements Response{
    private static final long serialVersionUID = 666;
    private String msg;
    private Status status;
    public AnswerMsg(){
        msg = "";
        status = Status.FINE;
    }
    public AnswerMsg clear(){
        msg = "";
        return this;
    }
    public AnswerMsg info(Object str){
        msg = str.toString();// + "\n";
        return this;
    }
    public AnswerMsg error(Object str){
        msg = "Error: " + str.toString();
        setStatus(Status.ERROR);
        return this;
    }
    public AnswerMsg setStatus(Status st){
        status = st;
        return this;
    }
    public String getMessage(){
        return msg;
    }
    public Status getStatus(){
        return status;
    }
    @Override
    public String toString(){
        if (getStatus() == Status.ERROR) {
            return "Err: " + getMessage();
        }
        return getMessage();
    }
}
