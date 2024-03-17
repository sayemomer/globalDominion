import models.GameState;
import phases.GameEngine;

import java.util.Scanner;

/**
 * The GameMenu class is responsible for handling the game menu and starting the game.
 */

public class GameMenu {

    /**
     * Constructor for the GameMenu class
     */

    GameMenu() {
    }

    /**
     * Scanner to read user input
     */
    private final Scanner l_scanner = new Scanner(System.in);

    /**
     * Start the game
     */

    public void start() {
        System.out.println("This is Global Dominion!");
        System.out.println("To start the game type 'start'. To exit type 'exit'. ");
        while (true) {
            String l_inputString = l_scanner.nextLine().trim().toLowerCase();
            if (l_inputString.equals("start")) {
                System.out.println("The game is starting...");

                GameEngine l_gameEngine = new GameEngine(new GameState(), l_scanner);
                l_gameEngine.mainGameLoop();

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
