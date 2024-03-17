package services;

import models.LogEntryBuffer;
import views.FileLogObserver;

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

     /**
      * Static block to initialize the log buffer and observer
      */

     static {
         logBuffer = new LogEntryBuffer();
         observer = new FileLogObserver("log_file.txt");
         logBuffer.attach(observer);
         logBuffer.log("Game started.");
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

//        logBuffer.attach(observer);
        logBuffer.log(pString);
    }


    /**
     * Method to print the string to the console and log the string to a file
     * @param outputString string to be printed
     */

    public static void println(StringBuilder outputString) {
        System.out.println(outputString);

//        logBuffer.attach(observer);
        logBuffer.log(String.valueOf(outputString));
    }

    /**
     * Method to print the string to the console and log the string to a file
     * @param outputString string to be printed
     */
    public static void print(StringBuilder outputString) {
        System.out.print(outputString);


//        logBuffer.attach(observer);
        logBuffer.log(String.valueOf(outputString));
    }

}
