package ru.potatocoder228.itmo.lab7.connection;

import jdk.nashorn.internal.ir.Block;
import ru.potatocoder228.itmo.lab7.commands.Command;
import ru.potatocoder228.itmo.lab7.commands.CommandManager;
import ru.potatocoder228.itmo.lab7.log.Log;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RequestHandler extends Thread{
    private volatile Ask ask;
    private volatile Answer answer;
    private Receiver receiver;

    public HashMap<String, String> clientInfo;
    private HashMap<String, Command> clientCommands;

    private CommandManager commandManager;

    private BlockingQueue<Answer> queue = new LinkedBlockingQueue<>();

    public RequestHandler(CommandManager commandManager, HashMap<String, String> clientInfo, HashMap<String, Command> clientCommands){
        this.commandManager = commandManager;
        this.clientInfo = clientInfo;
        this.clientCommands = clientCommands;

    }

    public void run() {
        synchronized (Receiver.class) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!receiver.getAskQueue().isEmpty()) {
                    ask = receiver.getAskQueue().poll();
                    String command = ask.getMessage();
                    String[] commandExecuter = command.split("\\s+", 2);
                    if (commandExecuter.length == 2) {
                        commandManager.setNewDragon(ask.getDragon());
                        commandManager.setCollectionInfo(clientInfo);
                        String clientMessage = commandManager.clientRun(commandExecuter[0], commandExecuter[1], clientCommands);
                        Log.logger.trace("Команда: " + command);
                        answer = new Answer();
                        answer.setMessage(clientMessage);
                        queue.add(answer);
                        System.out.println(answer.getMessage());
                        Log.logger.trace("Ответ обработан.");
                    } else if (commandExecuter.length == 1) {
                        commandManager.setCollectionInfo(clientInfo);
                        commandManager.setNewDragon(ask.getDragon());
                        String clientMessage = commandManager.clientRun(commandExecuter[0], "", clientCommands);
                        Log.logger.trace("Команда: " + command);
                        answer = new Answer();
                        answer.setMessage(clientMessage);
                        queue.add(answer);
                        System.out.println(answer.getMessage());
                        Log.logger.trace("Ответ обработан.");
                    }
            }
        }
    }
    public void setReceiver(Receiver receiver){
        this.receiver = receiver;
    }
    public BlockingQueue<Answer> getQueue(){
        return this.queue;
    }
}

