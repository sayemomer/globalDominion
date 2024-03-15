import config.AppConfig;
import config.Debug;

import models.LogEntryBuffer;
import views.FileLogObserver;

public class App {
    public static void main(String[] args) {

        AppConfig.setDebugMode(false);
        AppConfig.setVerboseMode(false);

        Debug.log("Game started.");
        Debug.log("verboseMode is " + (AppConfig.isVerboseMode() ? "on" : "off"));
        Debug.log("debugMode is " + (AppConfig.isDebugMode() ? "on" : "off"));


        LogEntryBuffer logBuffer = new LogEntryBuffer();
        FileLogObserver observer = new FileLogObserver("log_file.txt");
        logBuffer.attach(observer);
        logBuffer.log("Game started.");


        GameMenu gameMenu = new GameMenu();
        gameMenu.start();
    }
}
