package views;

import models.LogEntryBuffer;
import models.Observable;

import java.io.*;

/**
 * FileLogObserver class to update the observable state
 */

public class FileLogObserver implements Observer {
    private PrintWriter printWriter;

    /**
     * Constructor for FileLogObserver
     * @param path path of the file
     */

    public FileLogObserver(String path) {
        try {
            printWriter = new PrintWriter(new FileOutputStream(path, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to update the observable state
     * @param p_observable_state observable state
     */

    @Override
    public void update(Observable p_observable_state) {
        String log = ((LogEntryBuffer) p_observable_state).getLines();
        printWriter.println(log);
        printWriter.flush();
    }


}