package services;

import models.Country;
import models.LogEntryBuffer;
import views.FileLogObserver;

import java.util.Collection;

/**
 * CustomPrint class to print the output to the console and log the output to a file
 *
 */

public class CustomPrint {

    /**
     * Constructor for CustomPrint
     */

    CustomPrint() {
    }

    /**
     * LogEntryBuffer and FileLogObserver objects to log the output to a file
     */
    static LogEntryBuffer logBuffer;

    static FileLogObserver observer;

     static {
         logBuffer = new LogEntryBuffer();
         observer = new FileLogObserver("log_file.txt");
         logBuffer.attach(observer);
         logBuffer.log("Game started.");
     }


    /**
     * Method to print the empty line to the console and log the empty string to a file
     */
    public static void println() {
        System.out.println();
        logBuffer.log("");
    }

    /**
     * Method to print the string to the console and log the string to a file
     * @param pString string to be printed
     */
    public static void println(String pString) {
        System.out.println(pString);
        logBuffer.log(pString);
    }

    /**
     * Method to print the string to the console and log the string to a file
     * @param pString string to be printed
     */

    public static void print(String pString) {
        System.out.print(pString);
        logBuffer.log(pString);
    }


    /**
     * Method to print the string to the console and log the string to a file
     * @param outputString string to be printed
     */

    public static void println(StringBuilder outputString) {
        System.out.println(outputString);
        logBuffer.log(String.valueOf(outputString));
    }

    /**
     * Method to print the string to the console and log the string to a file
     * @param outputString string to be printed
     */
    public static void print(StringBuilder outputString) {
        System.out.print(outputString);
        logBuffer.log(String.valueOf(outputString));
    }
    /**
     * Prints the contents of the given collection of countries to the console and logs it to the log buffer.
     *
     * @param values The collection of countries to print and log.
     */
    public static void println(Collection<Country> values) {
        System.out.print(values);

        logBuffer.log(String.valueOf(values));
    }
}
