package models;

import java.util.Observable;

class LogEntryBuffer extends Observable {
    private StringBuilder log = new StringBuilder();

    public void logAction(String action) {
        log.append(action).append("\n");
        setChanged();
        notifyObservers(action);
    }

}


