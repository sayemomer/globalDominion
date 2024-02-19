package config;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Debug class is responsible for logging messages to the console and a log file.
 */
public class Debug {
    private static final String logFile = "log.txt";

    public static void log(String message) {
        if (AppConfig.isDebugMode()) {
            // TODO: Add color to the log messages in Windows
            if (System.getProperty("os.name").toLowerCase().contains("mac") || System.getProperty("os.name").toLowerCase().contains("linux")) {
                System.out.println("\u001B[35m" + message + "\u001B[0m");
            } else {
                System.out.println(message);
            }
        }
        if (AppConfig.isVerboseMode()) {
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
