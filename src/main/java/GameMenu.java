import models.GameState;

import java.util.Scanner;

/**
 * The GameMenu class is responsible for handling the game menu and starting the game.
 */

public class GameMenu {
    private final Scanner l_scanner = new Scanner(System.in);

    public void start() {
        System.out.println("This is Global Dominion!");
        System.out.println("To start the game write 'start'. To exit write 'exit'. ");
        while (true) {
            String l_inputString = l_scanner.nextLine();
            if (l_inputString.equals("start")) {
                System.out.println("The game is starting...");

                GameEngine l_gameEngine = new GameEngine(new GameState(), l_scanner);

                break;
            } else if (l_inputString.equals("exit")) {
                System.out.println("Exiting the game...");
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
}
