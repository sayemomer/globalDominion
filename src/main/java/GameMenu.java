import models.GameState;
import phases.GameEngine;
import phases.TournamentStartupPhase;
import services.CustomPrint;

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
        CustomPrint.println("This is Global Dominion!");
        CustomPrint.println("select: \n1. Single Game Mode\n2. Tournament Mode\n3. Exit");
        while (true) {
            String l_inputString = l_scanner.nextLine().trim().toLowerCase();
            if (l_inputString.equals("1")) {
                CustomPrint.println("The game is starting...");

                GameEngine l_gameEngine = new GameEngine(new GameState(), l_scanner);
                l_gameEngine.mainGameLoop();

                break;
            } else if (l_inputString.equals("2")) {
                CustomPrint.println("The tournament is starting...");
                GameEngine l_gameEngine = new GameEngine(new GameState(), l_scanner);
                l_gameEngine.setGamePhase(new TournamentStartupPhase(l_gameEngine));
                l_gameEngine.mainGameLoop();

                break;
            }
            else if (l_inputString.equals("3")) {
                CustomPrint.println("Exiting the game...");
                break;
            } else {
                CustomPrint.println("Invalid input. Please try again.");
            }
        }
    }
}
