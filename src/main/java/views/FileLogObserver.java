package views;

import models.LogEntryBuffer;
import models.Observable;

import java.io.*;

public class FileLogObserver implements Observer {
    private PrintWriter printWriter;

    public FileLogObserver(String path) {
        try {
            printWriter = new PrintWriter(new FileOutputStream(path, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable p_observable_state) {
        String log = ((LogEntryBuffer) p_observable_state).getLines();
        printWriter.println(log);
        printWriter.flush();
    }


}