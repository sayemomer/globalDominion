package services;

import models.LogEntryBuffer;
import views.FileLogObserver;

public class CustomPrint {


     static LogEntryBuffer logBuffer;
     static FileLogObserver observer;

     static {
         logBuffer = new LogEntryBuffer();
         observer = new FileLogObserver("log_file.txt");
         logBuffer.attach(observer);
         logBuffer.log("Game started.");
     }



    public static void println(String pString) {
        System.out.println(pString);


//        logBuffer.attach(observer);
        logBuffer.log(pString);
    }

    public static void print(String pString) {
        System.out.print(pString);

//        logBuffer.attach(observer);
        logBuffer.log(pString);
    }

    public static void println(StringBuilder outputString) {
        System.out.println(outputString);

//        logBuffer.attach(observer);
        logBuffer.log(String.valueOf(outputString));
    }
    public static void print(StringBuilder outputString) {
        System.out.print(outputString);


//        logBuffer.attach(observer);
        logBuffer.log(String.valueOf(outputString));
    }

}
