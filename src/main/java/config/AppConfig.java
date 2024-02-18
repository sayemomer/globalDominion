package config;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppConfig {
    private static boolean debugMode = false;
    private static boolean verboseMode = false;
    private static boolean testMode = false;

    // Private constructor to prevent instantiation
    private AppConfig() {
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static boolean isVerboseMode() {
        return verboseMode;
    }

    public static boolean isTestMode() {
        return testMode;
    }

    public static void setVerboseMode(boolean verboseMode) {
        AppConfig.verboseMode = verboseMode;
    }


    public static void setDebugMode(boolean debugMode) {
        AppConfig.debugMode = debugMode;
    }

}