import config.AppConfig;
import config.Debug;

import models.LogEntryBuffer;
import services.CustomPrint;
import views.FileLogObserver;

public class App {
    public static void main(String[] args) {

        AppConfig.setDebugMode(true);
        AppConfig.setVerboseMode(false);

        Debug.log("Game started.");
        Debug.log("verboseMode is " + (AppConfig.isVerboseMode() ? "on" : "off"));
        Debug.log("debugMode is " + (AppConfig.isDebugMode() ? "on" : "off"));



        GameMenu gameMenu = new GameMenu();
        gameMenu.start();
    }
}
