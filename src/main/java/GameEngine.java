import config.AppConfig;
import config.Debug;
import controllers.*;
import models.GameState;
import models.Player;
import models.orders.Order;

import java.util.*;

/**
 * The GameEngine class is responsible for handling the game loop and the game phases.
 * It utilizes the PlayerController and MapController classes to handle the game phases.
 */

public class GameEngine {

    Scanner scanner;
    PlayerController playerController;
    MapController mapController;
    CountryController countryController;
    OrderController orderController;
    GameState gameState;

    /**
     * This is the constructor for the GameEngine class.
     *
     * @param p_gameState The game state.
     * @param p_scanner   The scanner object.
     */

    public GameEngine(GameState p_gameState, Scanner p_scanner) {
        gameState = p_gameState;
        scanner = p_scanner;

        playerController = new PlayerController(gameState);
        mapController = new MapController(gameState);
        countryController = new CountryController(gameState);
        orderController = new OrderController(gameState, scanner);

        int phaseResult = 0;
        phaseResult = startUpPhase();
        gameState.assignReinforcements();


        // print number of reinforcements for each player
        for (Player player : gameState.getPlayers().values()) {
            Debug.log(player.getName() + " has " + player.getReinforcementPoll() + " reinforcements.");

        }

        issueOrdersPhase();
        executeOrdersPhase();
    }

    /**
     * This method is responsible for the startup phase of the game.
     *
     * @return 0 if the game should proceed to the next phase, 1 if the game should exit.
     */


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

            //split the input string into command and arguments

            String[] l_inputString = scanner.nextLine().toLowerCase().split("\\s+");
            String l_command = l_inputString[0];
            String[] l_args = Arrays.copyOfRange(l_inputString, 1, l_inputString.length);

            if (l_command.equals("proceed")) {
                System.out.println("Proceeding to the next phase...");
                break;
            } else if (l_command.equals("exit")) {
                System.out.println("Exiting the game...");
            } else if (l_command.equals("help")) {

                System.out.println("Available commands: ");
                System.out.println("  " + Command.GAME_PLAYER_SYNTAX);
                System.out.println("  " + Command.LOAD_MAP_SYNTAX);
                System.out.println("  " + Command.ASSIGN_COUNTRIES_SYNTAX);
            } else if (l_command.equals(Command.SHOW_MAP)) {
                gameState.printMap();
                System.out.println("  " + Command.EDIT_MAP_SYNTAX);
                System.out.println("  " + Command.ASSIGN_COUNTRIES_SYNTAX);
            } else if (l_command.equals(Command.LOAD_MAP)) {
                if (mapController.handleloadMapCommand(l_args))
                    mapEditPhase();
            } else if (l_command.equals(Command.EDIT_MAP)) {
                if (mapController.handleEditMapCommand(l_args))
                    mapEditPhase();
            } else if (l_command.equals(Command.ASSIGN_COUNTRIES)) {
                countryController.handleAssignCountriesCommand(l_args);
            } else if (l_command.equals(Command.GAME_PLAYER)) {
                playerController.handleGamePlayerCommand(l_args);
            } else if (l_command.equals("back")) {
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
        System.out.println(" - Validate the map.");
        System.out.println(" - Save the map.");
        System.out.println(" - Show the map.");
        System.out.println("Type 'help' to see the list of available commands.");

        while (true) {

            String[] l_inputString = scanner.nextLine().toLowerCase().split("\\s+");
            String l_command = l_inputString[0];
            String[] l_args = Arrays.copyOfRange(l_inputString, 1, l_inputString.length);


            if (l_command.equals("proceed")) {
                System.out.println("Proceeding to the next phase...");
                break;
            } else if (l_command.equals("exit")) {
                System.out.println("Exiting the game...");
                return;
            } else if (l_command.equals("help")) {
                System.out.println("Available commands: ");
                System.out.println("  " + Command.EDIT_CONTINENT_SYNTAX);
                System.out.println("  " + Command.EDIT_COUNTRY_SYNTAX);
                System.out.println("  " + Command.EDIT_NEIGHBOR_SYNTAX);
                System.out.println("  " + Command.VALIDATE_MAP_SYNTAX);
                System.out.println("  " + Command.SAVE_MAP_SYNTAX);
                System.out.println("  " + Command.SHOW_MAP_SYNTAX);

            } else if (l_command.equals(Command.VALIDATE_MAP)) {
                mapController.handleValidateMapCommand(Arrays.copyOfRange(l_inputString, 1, l_inputString.length));
            } else if (l_command.equals(Command.EDIT_CONTINENT)) {
                countryController.handleEditContinentCommand(Arrays.copyOfRange(l_inputString, 1, l_inputString.length));
            } else if (l_command.equals(Command.EDIT_COUNTRY)) {
                countryController.handleEditCountryCommand(Arrays.copyOfRange(l_inputString, 1, l_inputString.length));
            } else if (l_command.equals(Command.EDIT_NEIGHBOR)) {
                countryController.handleEditNeighborCommand(Arrays.copyOfRange(l_inputString, 1, l_inputString.length));
            } else if (l_inputString[0].equals(Command.SAVE_MAP)) {
                mapController.handleSaveMapCommand(l_args);
            } else if (l_command.equals(Command.SHOW_MAP)) {
                gameState.printMap();
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    /**
     * This method is responsible for the game loop.
     */


    void issueOrdersPhase() {
        Debug.log("issueOrdersPhase() called.");
        // playerFinishedOrders of a player is true if the player has finished issuing orders
        Map<String, Boolean> playerFinishedOrders = new HashMap<>();
        for (Player player : gameState.getPlayers().values()) {
            playerFinishedOrders.put(player.getName(), false);
        }

        System.out.println("*-*-* ISSUE ORDERS PHASE *-*-*");
        System.out.println("In this phase you can: ");
        System.out.println(" - Deploy reinforcements");
        System.out.println("Type 'help' to see the list of available commands.");
        System.out.println("Type 'ready' to finish ordering.");
        System.out.println("Type 'exit' to exit the game.");

        boolean aPlayerOrdered = true;
        // as long as a player has not finished ordering
        while (aPlayerOrdered) {
            aPlayerOrdered = false;
            for (Player player : gameState.getPlayers().values()) {
                if (playerFinishedOrders.get(player.getName())) {
                    continue;
                }
                aPlayerOrdered = true;
                player.issueOrder();
                // only for build one where just deploy order is available
                playerFinishedOrders.put(player.getName(), player.getReinforcementPoll() == 0);
            }
        }
    }

    void executeOrdersPhase() {
        System.out.println("Executing orders...");
        boolean anOrderExecuted = true;
        while (anOrderExecuted) {
            anOrderExecuted = false;
            for (Player player : gameState.getPlayers().values()) {
                if (player.getOrders().isEmpty())
                    continue;

                Order order = player.nextOrder();
                order.execute();
                anOrderExecuted = true;
            }
        }
    }
}