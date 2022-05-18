package ru.potatocoder228.itmo.lab6.data;

public interface Collectionable extends Comparable<Collectionable>, Validateable {
    public int getId();

    public void setId();

    public String getName();

    public int getAge();

    public int compareTo(Collectionable dragon);

    public boolean validate();
}
