package models;

public class LogEntryBuffer extends Observable {
    private StringBuilder lines = new StringBuilder();

    public String getLines() {
        return lines.toString();
    }

    public void log(String action) {
        lines.append(action).append("\n");
        notifyObservers(this);
        flush();
    }

    public void flush() {
        lines = new StringBuilder();
    }

}


