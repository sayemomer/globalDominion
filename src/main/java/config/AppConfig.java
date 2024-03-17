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

    /**
     * This method is used to get the debug mode.
     * @return  debug mode
     */

    public static boolean isDebugMode() {
        return debugMode;
    }

    /**
     * This method is used to get the verbose mode.
     * @return  verbose mode
     */

    public static boolean isVerboseMode() {
        return verboseMode;
    }

    /**
     * This method is used to get the test mode.
     * @return  test mode
     */

    public static boolean isTestMode() {
        return testMode;
    }

    /**
     * This method is used to set the Verbose mode.
     * @param p_verboseMode test mode
     */

    public static void setVerboseMode(boolean p_verboseMode) {
        AppConfig.verboseMode = p_verboseMode;
    }

    /**
     * This method is used to set the debug mode.
     * @param p_debugMode test mode
     */


    public static void setDebugMode(boolean p_debugMode) {
        AppConfig.debugMode = p_debugMode;
    }

}