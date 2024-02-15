import config.AppConfig;
import config.Debug;

public class App {
    public static void main(String[] args) {

        AppConfig.setDebugMode(true);
        AppConfig.setVerboseMode(true);

        Debug.log("Game started.");
        Debug.log("verboseMode is " + (AppConfig.isVerboseMode() ? "on" : "off"));
        Debug.log("debugMode is " + (AppConfig.isDebugMode() ? "on" : "off"));

        GameMenu gameMenu = new GameMenu();
        gameMenu.start();
    }
}
