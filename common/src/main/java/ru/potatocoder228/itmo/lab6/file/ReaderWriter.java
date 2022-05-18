package ru.potatocoder228.itmo.lab6.file;

import ru.potatocoder228.itmo.lab6.exceptions.FileException;

public interface ReaderWriter {
    public void setPath(String pth);

    public String read() throws FileException;

    public void write(String data) throws FileException;
}
