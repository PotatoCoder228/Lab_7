package ru.potatocoder228.itmo.lab7.data;

import ru.potatocoder228.itmo.lab7.database.DragonDatabaseManager;
import ru.potatocoder228.itmo.lab7.user.User;

import java.time.LocalDateTime;
import java.util.*;

public class CollectionManager {
    private final LinkedList<Dragon> collection;
    private final LinkedHashSet<Integer> idList = new LinkedHashSet<>();
    private User user;
    private DragonDatabaseManager dragonManager;
    private Dragon newDragon;
    private LocalDateTime creatingTime;
    private HashMap<String, String> map;

    public CollectionManager(LinkedList<Dragon> collection) {
        this.collection = collection;
        Collections.sort(collection);
    }

    public CollectionManager() {
        collection = new LinkedList<>();
        creatingTime = LocalDateTime.now();
    }

    public void addWithoutIdGeneration(Dragon worker) {
        idList.add(worker.getId());
        collection.add(worker);
    }

    public void setDragon(Dragon dragon) {
        this.newDragon = dragon;
    }

    public Dragon getNewDragon() {
        return this.newDragon;
    }

    public int getSize() {
        return collection.size();
    }

    public LocalDateTime getCreatingTime() {
        return this.creatingTime;
    }

    public HashMap<String, String> getInfo() {
        return this.map;
    }

    public void setInfo(HashMap<String, String> map) {
        this.map = map;
    }

    public LinkedList<Dragon> getCollection() {
        return this.collection;
    }

    public void setDragonManager(DragonDatabaseManager dragonManager) {
        this.dragonManager = dragonManager;
    }

    public DragonDatabaseManager getDragonManager() {
        return this.dragonManager;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

}
