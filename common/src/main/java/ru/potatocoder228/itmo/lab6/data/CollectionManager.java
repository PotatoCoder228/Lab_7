package ru.potatocoder228.itmo.lab6.data;

import ru.potatocoder228.itmo.lab6.commands.Command;
import ru.potatocoder228.itmo.lab6.file.FileManager;

import java.time.LocalDateTime;
import java.util.*;

public class CollectionManager {
    private LinkedList<Dragon> collection;
    private LinkedHashSet<Integer> idList = FileManager.idList;
    private FileManager fileManager;
    private Dragon newDragon;
    private LocalDateTime creatingTime = LocalDateTime.now();
    private HashMap<String, String> map;

    public CollectionManager(LinkedList<Dragon> collection) {
        this.collection = collection;
        Collections.sort(collection);
    }

    public LinkedHashSet<Integer> getIdList(){
        return this.idList;
    }
    public void setDragon(Dragon dragon){
        this.newDragon = dragon;
    }
    public Dragon getNewDragon(){
        return this.newDragon;
    }
    public int getSize() {
        return collection.size();
    }

    public String getType() {
        return Dragon.class.getName();
    }
    public LocalDateTime getCreatingTime(){
        return this.creatingTime;
    }

    public Dragon getFirst() {
        try {
            return collection.getFirst();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public Dragon getLast() {
        try {
            return collection.getLast();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
    public void addLast(Dragon dragon) {
        collection.add(dragon);
    }
    public void clear(){
        collection.clear();
    }
    public void saveCollection(){
        fileManager.writeObjects(collection);
    }
    public void setFileManager(FileManager fileManager){
        this.fileManager = fileManager;
    }
    public void setInfo(HashMap<String, String> map){
        this.map = map;
    }
    public HashMap<String, String> getInfo(){
        return this.map;
    }
    public LinkedList<Dragon> getCollection(){
        return this.collection;
    }
}
