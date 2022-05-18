package ru.potatocoder228.itmo.lab6.data;

public class DefaultDragon extends Dragon {
    public DefaultDragon(String nm, Coordinates coordinates, int age, String description, Boolean speaking, DragonType type, DragonCave cave) {
        super(nm, coordinates, age, description, speaking, type, cave);
        setCreationDate();
    }
}
