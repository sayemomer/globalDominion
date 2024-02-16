package config;

public class Printer{
    public static void PrintLn(String message) {
        if (!AppConfig.isTestMode()) {
            System.out.println(message);
        }
    }
}
