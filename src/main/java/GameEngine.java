import controllers.Command;
import controllers.MapController;
import controllers.PlayerController;
import models.GameState;
import models.Player;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameEngine {

    Scanner scanner;
    PlayerController playerController;
    MapController mapController;
    GameState gameState;

    public GameEngine(GameState p_gameState, Scanner p_scanner) {
        gameState = p_gameState;
        scanner = p_scanner;

        playerController = new PlayerController(gameState);
        mapController = new MapController(gameState);

        int phaseResult = 0;
        phaseResult = startUpPhase();
        if (phaseResult == 1) {
            return;
        }

    }

    int startUpPhase() {
        System.out.println("*-*-* STARTUP PHASE *-*-*");
        System.out.println("In this phase you can: ");
        System.out.println(" - Add/Remove players to/from the game.");
        System.out.println(" - Assign countries to players.");
        System.out.println(" - Load and edit maps.");
        System.out.println("Type 'help' to see the list of available commands.");
        System.out.println("Type 'proceed' to proceed to the next phase.");
        System.out.println("Type 'back' to go back to the main menu.");

        while (true) {

            String[] inputString = scanner.nextLine().toLowerCase().split("\\s+");

            if (inputString[0].equals("proceed")) {
                System.out.println("Proceeding to the next phase...");
                break;
            } else if (inputString[0].equals("exit")) {
                System.out.println("Exiting the game...");
                return 1;
            } else if (inputString[0].equals("help")) {
                System.out.println("Available commands: ");
                System.out.println("  " + Command.GAME_PLAYER_SYNTAX);
//                System.out.println("  " + Command.LOAD_MAP_SYNTAX);
                System.out.println("  " + Command.ASSIGN_COUNTRIES_SYNTAX);
            } else if (inputString[0].equals(Command.GAME_PLAYER)) {
                playerController.handleGamePlayerCommand(Arrays.copyOfRange(inputString, 1, inputString.length));
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
        return 0;

    }


    void startGameLoop() {
        System.out.println("Game loop is running...");
        assignReinforcementsPhase();
        issueOrdersPhase();
        executeOrdersPhase();
    }

    void assignReinforcementsPhase() {
        System.out.println("Assigning reinforcements...");
    }

    void issueOrdersPhase() {
        System.out.println("Issuing orders...");
    }

    void executeOrdersPhase() {
        System.out.println("Executing orders...");
    }

}
