package config;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppConfig {
    private static boolean debugMode = false;
    private static boolean verboseMode = false;
    private static final String logFile = "log.txt";

    // Private constructor to prevent instantiation
    private AppConfig() {
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static boolean isVerboseMode() {
        return verboseMode;
    }

    public static void setVerboseMode(boolean verboseMode) {
        AppConfig.verboseMode = verboseMode;
    }


    public static void setDebugMode(boolean debugMode) {
        AppConfig.debugMode = debugMode;
    }

    public static void log(String message) {
        if (debugMode) {
            // TODO: Add color to the log messages in Windows
            if (System.getProperty("os.name").toLowerCase().contains("mac") || System.getProperty("os.name").toLowerCase().contains("linux")) {
                System.out.println("\u001B[35m" + message + "\u001B[0m");
            } else {
                System.out.println(message);
            }
        }
        if (verboseMode) {
            try (PrintWriter logStream = new PrintWriter(new FileWriter(logFile, true))) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateTime = dateFormat.format(new Date());

                // Print date, time, and message to the log file
                logStream.println(dateTime + " - " + message);
            } catch (Exception e) {
                System.out.println("An error occurred while writing to the log file. " + e.getMessage());
                System.out.println("Try running the program with verboseMode=false .");
            }

        }
    }
}