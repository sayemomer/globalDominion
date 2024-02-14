import config.AppConfig;

public class App {
    public static void main(String[] args) {

        AppConfig.setDebugMode(true);
        AppConfig.setVerboseMode(true);
        AppConfig.log("Game started.");
        AppConfig.log("verboseMode is " + (AppConfig.isVerboseMode() ? "on" : "off"));
        AppConfig.log("debugMode is " + (AppConfig.isDebugMode() ? "on" : "off"));

        GameMenu gameMenu = new GameMenu();
        gameMenu.start();
    }
}
