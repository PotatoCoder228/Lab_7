package ru.potatocoder228.itmo.lab7.data;

public interface Collectionable extends Comparable<Collectionable>, Validateable {
    int getId();
    void setId();

    String getName();

    int getAge();

    int compareTo(Collectionable dragon);

    boolean validate();
}
