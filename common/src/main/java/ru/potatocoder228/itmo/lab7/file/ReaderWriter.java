package ru.potatocoder228.itmo.lab7.file;

import ru.potatocoder228.itmo.lab7.exceptions.FileException;

public interface ReaderWriter {
    void setPath(String pth);

    String read() throws FileException;

    void write(String data) throws FileException;
}
