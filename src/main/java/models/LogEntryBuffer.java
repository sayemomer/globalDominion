package models;

import java.util.Observable;
import java.util.Observer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogEntryBuffer extends Observable {
    private StringBuilder log = new StringBuilder();

    public void logAction(String action) {
        log.append(action).append("\n");
        setChanged();
        notifyObservers(action);
    }

}


