import models.GameState;

import java.util.Scanner;

public class GameMenu {
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("This is Global Dominion!");
        System.out.println("To start the game write 'start'. To exit write 'exit'. ");
        while (true) {
            String inputString = scanner.nextLine();
            if (inputString.equals("start")) {
                System.out.println("The game is starting...");
                GameEngine gameEngine = new GameEngine(new GameState(), scanner);
                break;
            } else if (inputString.equals("exit")) {
                System.out.println("Exiting the game...");
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
}
