package views;

import models.LogEntryBuffer;

import java.io.BufferedWriter;
import java.util.Observable;
import java.util.Observer;
import java.io.FileWriter;
import java.io.IOException;

class FileLogObserver implements Observer {
    private String logFilePath;

    public FileLogObserver(String path) {
        logFilePath = path;
    }

    @Override
    public void update(Observable o, Object arg) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write((String) arg);
            writer.newLine();
        } catch (IOException e) {
        }
    }
}