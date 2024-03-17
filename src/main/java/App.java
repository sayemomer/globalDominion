import config.AppConfig;
import config.Debug;

import models.LogEntryBuffer;
import services.CustomPrint;
import views.FileLogObserver;

/**
 * This is the main class of the game.
 * It is used to start the game.
 */
public class App {

    /**
     * Constructor for the App class
     */

    App() {
    }

    /**
     * This is the main method of the game.
     * It is used to start the game.
     * @param args command line arguments
     */
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
