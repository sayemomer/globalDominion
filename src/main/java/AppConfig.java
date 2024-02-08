public class AppConfig {
    private static boolean debugMode = false;

    // Private constructor to prevent instantiation
    private AppConfig() {
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static void setDebugMode(boolean debugMode) {
        AppConfig.debugMode = debugMode;
    }
}