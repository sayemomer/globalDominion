import controllers.Command;
import controllers.CountryController;
import controllers.MapController;
import controllers.PlayerController;
import models.GameState;

import java.util.Arrays;
import java.util.Scanner;

/**
 * The GameEngine class is responsible for handling the game loop and the game phases.
 * It utilizes the PlayerController and MapController classes to handle the game phases.
 */

public class GameEngine {

    Scanner scanner;
    PlayerController playerController;
    MapController mapController;
    CountryController countryController;
    GameState gameState;

    public GameEngine(GameState p_gameState, Scanner p_scanner) {
        gameState = p_gameState;
        scanner = p_scanner;

        playerController = new PlayerController(gameState);
        mapController = new MapController(gameState);
        countryController = new CountryController(gameState);

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
            String command = inputString[0];
            String[] args = Arrays.copyOfRange(inputString, 1, inputString.length);

            if (command.equals("proceed")) {
                System.out.println("Proceeding to the next phase...");
                break;
            } else if (command.equals("exit")) {
                System.out.println("Exiting the game...");
                return 1;
            } else if (command.equals("help")) {
                System.out.println("Available commands: ");
                System.out.println("  " + Command.GAME_PLAYER_SYNTAX);
                System.out.println("  " + Command.LOAD_MAP_SYNTAX);
                System.out.println("  " + Command.ASSIGN_COUNTRIES_SYNTAX);
            } else if (command.equals(Command.GAME_PLAYER)) {
                playerController.handleGamePlayerCommand(Arrays.copyOfRange(inputString, 1, inputString.length));
            } else if (command.equals(Command.LOAD_MAP)) {
                mapController.handleLoadMapCommand(args);
            } else if (command.equals(Command.SHOW_MAP)) {
                gameState.printMap();
                System.out.println("  " + Command.EDIT_MAP_SYNTAX);
                System.out.println("  " + Command.ASSIGN_COUNTRIES_SYNTAX);
            } else if (command.equals(Command.LOAD_MAP)) {
                if (mapController.handleLoadMapCommand(args))
                    mapEditPhase();
            } else if (command.equals(Command.EDIT_MAP)) {
                if (mapController.handleEditMapCommand(args))
                    mapEditPhase();
            } else if (command.equals(Command.ASSIGN_COUNTRIES)) {
                countryController.handleAssignCountriesCommand(args);
            } else if (command.equals("back")) {
                return 1;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
        return 0;
    }

    /**
     * This method is responsible for the map edit phase of the game.
     */

    private void mapEditPhase() {
        System.out.println("*-*-* MAP EDIT *-*-*");
        System.out.println("location: " + mapController.getFilePath());
        System.out.println("In this phase you can: ");
        System.out.println(" - Add/Remove continents to/from the map.");
        System.out.println(" - Add/Remove countries to/from the map.");
        System.out.println(" - Add/Remove connections between countries.");
        System.out.println("Type 'help' to see the list of available commands.");

        while (true) {

            String[] inputString = scanner.nextLine().toLowerCase().split("\\s+");
            String command = inputString[0];
            String[] args = Arrays.copyOfRange(inputString, 1, inputString.length);


            if (command.equals("proceed")) {
                System.out.println("Proceeding to the next phase...");
                break;
            } else if (command.equals("exit")) {
                System.out.println("Exiting the game...");
                return;
            } else if (command.equals("help")) {
                System.out.println("Available commands: ");
                System.out.println("  " + Command.EDIT_CONTINENT_SYNTAX);
                System.out.println("  " + Command.EDIT_COUNTRY_SYNTAX);
                System.out.println("  " + Command.EDIT_NEIGHBOR_SYNTAX);
                System.out.println("  " + Command.VALIDATE_MAP_SYNTAX);
                System.out.println("  " + Command.SAVE_MAP_SYNTAX);
                System.out.println("  " + Command.SHOW_MAP_SYNTAX);
            } else if (command.equals(Command.VALIDATE_MAP)) {
                mapController.handleValidateMapCommand(Arrays.copyOfRange(inputString, 1, inputString.length));
            } else if (command.equals(Command.EDIT_CONTINENT)) {
                countryController.handleEditContinentCommand(Arrays.copyOfRange(inputString, 1, inputString.length));
            } else if (command.equals(Command.EDIT_COUNTRY)) {
                countryController.handleEditCountryCommand(Arrays.copyOfRange(inputString, 1, inputString.length));
            } else if (command.equals(Command.EDIT_NEIGHBOR)) {
                countryController.handleEditNeighborCommand(Arrays.copyOfRange(inputString, 1, inputString.length));
            } else if (command.equals(Command.SAVE_MAP)) {
                mapController.handleSaveMapCommand(args);
            } else if (command.equals(Command.SHOW_MAP)) {
                gameState.printMap();
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
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
