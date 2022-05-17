package ru.potatocoder228.itmo.lab6.data;

import ru.potatocoder228.itmo.lab6.file.FileManager;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class CollectionManager {
    private LinkedList<Dragon> collection;
    private LinkedHashSet<Integer> idList = FileManager.idList;
    public CollectionManager(LinkedList<Dragon> collection){
        this.collection = collection;
    }
    public int getSize() {
        return collection.size();
    }

    public String getType() { return Dragon.class.getName(); }
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
    public Dragon getById(int id) {
        for (Dragon dragon : collection) {
            if (dragon.getId() == id) {
                return dragon;
            }
        }
        return null;
    }
    public void addLast(Dragon dragon) {
        collection.add(dragon);//TODO id
    }
    public void addById(Dragon dragon, int id) {
        collection.add(id, dragon);//TODO
    }
}
