package models;

import java.util.Observable;
import java.util.Observer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class LogEntryBuffer extends Observable {
    private StringBuilder log = new StringBuilder();

    public void logAction(String action) {
        log.append(action).append("\n");
        setChanged();
        notifyObservers(action);
    }

}

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
