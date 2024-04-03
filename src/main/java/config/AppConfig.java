package config;

/**
 * responsible for saving running mode of the application
 */
public class AppConfig {
    private static boolean debugMode = false;
    private static boolean verboseMode = false;
    private static boolean testMode = false;
    private static boolean tournamentMode = false;

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

    /**
     * Checks if the application is in tournament mode.
     *
     * This method retrieves the current status of the tournament mode, indicating whether the application
     * is operating in tournament mode or not.
     *
     * @return A boolean value indicating whether the application is in tournament mode (true) or not (false).
     */
    public static boolean isTournamentMode() {
        return tournamentMode;
    }

    /**
     * Sets the tournament mode status.
     *
     * This method allows setting the tournament mode status, which determines whether the application
     * operates in tournament mode or not.
     *
     * @param p_tournamentMode A boolean value indicating whether the tournament mode should be enabled or disabled.
     */
    public static void setTournamentMode(boolean p_tournamentMode) {
        AppConfig.tournamentMode = p_tournamentMode;
    }

}