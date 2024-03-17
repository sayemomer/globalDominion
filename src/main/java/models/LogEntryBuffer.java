package models;

/**
 * LogEntryBuffer class to store the log entries
 * extends Observable
 * notifies the observer when the log is updated
 * notifies the observer when the log is flushed
 */

public class LogEntryBuffer extends Observable {

    /**
     * Constructor for LogEntryBuffer
     */

    public LogEntryBuffer() {
    }

    /**
     * StringBuilder to store the log entries
     */
    private StringBuilder lines = new StringBuilder();

    /**
     * Method to get the log entries
     * @return log entries
     */

    public String getLines() {
        return lines.toString();
    }

    /**
     * Method to log the action
     * @param action action to be logged
     */

    public void log(String action) {
        lines.append(action).append("\n");
        notifyObservers(this);
        flush();
    }

    /**
     * Method to flush the log entries
     */

    public void flush() {
        lines = new StringBuilder();
    }

}


