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


        // print number of reinforcements for each player
        for (Player player : gameState.getPlayers().values()) {
            Debug.log(player.getName() + " has " + player.getReinforcementPoll() + " reinforcements.");

        }

    }

    public void mainGameLoop() {
        // This loop continues until the user has 1. loaded a valid map, 2. added players, and 3. assigned countries.
        while (true) {
            startUpPhase();
            if (!gameState.isActionDone(GameState.GameAction.VALID_MAP_LOADED)) {
                System.out.println("Map is not valid. Please try again.");
                continue;
            }
            if (!gameState.isActionDone(GameState.GameAction.PlAYERS_ADDED)) {
                System.out.println("Players are not added. Please try again.");
                continue;
            }
            if (!gameState.isActionDone(GameState.GameAction.COUNTRIES_ASSIGNED)) {
                System.out.println("Countries are not assigned. Please try again.");
                continue;
            }
            break;
        }
        gameState.assignReinforcements();
        issueOrdersPhase();
        executeOrdersPhase();
    }

    /**
     * This method is responsible for the startup phase of the game.
     */
    void startUpPhase() {
        System.out.println("*-*-* STARTUP PHASE *-*-*");
        System.out.println("Available commands: ");
        System.out.println("  " + Command.GAME_PLAYER_SYNTAX);
        System.out.println("  " + Command.LOAD_MAP_SYNTAX);
        System.out.println("  " + Command.EDIT_MAP_SYNTAX);
        System.out.println("  " + Command.ASSIGN_COUNTRIES_SYNTAX);
        System.out.println("Type 'proceed' to proceed to the next phase.");
        System.out.println("Type 'exit' to exit the game.");

        label:
        while (true) {
            System.out.print("startup-phase> ");
            String[] l_inputString = scanner.nextLine().trim().toLowerCase().split("\\s+");
            String l_command = l_inputString[0];
            String[] l_args = Arrays.copyOfRange(l_inputString, 1, l_inputString.length);

            switch (l_command) {
                case "proceed":
                    System.out.println("Proceeding to the next phase...");
                    break label;
                case "exit":
                    System.out.println("Exiting the game...");
                    System.exit(0);
                case Command.SHOW_MAP:
                    gameState.printMap();
                    System.out.println("  " + Command.EDIT_MAP_SYNTAX);
                    System.out.println("  " + Command.ASSIGN_COUNTRIES_SYNTAX);
                    break;
                case Command.GAME_PLAYER:
                    playerController.handleGamePlayerCommand(l_args);
                    break;
                case Command.LOAD_MAP:
                    if (mapController.handleloadMapCommand(l_args)) mapEditPhase();
                    break;
                case Command.EDIT_MAP:
                    if (mapController.handleEditMapCommand(l_args)) mapEditPhase();
                    break;
                case Command.ASSIGN_COUNTRIES:
                    countryController.handleAssignCountriesCommand(l_args);
                    break;
                default:
                    System.err.println("Invalid input. Please try again.");
                    break;
            }
        }
    }

    /**
     * This method is responsible for the map edit phase of the game.
     * gets user input and calls the appropriate method in the controllers.
     */
    private void mapEditPhase() {

        System.out.println("*-*-* MAP EDITOR *-*-*");
        System.out.println("file location: " + mapController.getFilePath());
        System.out.println("Available commands: ");
        System.out.println("  " + Command.EDIT_CONTINENT_SYNTAX);
        System.out.println("  " + Command.EDIT_COUNTRY_SYNTAX);
        System.out.println("  " + Command.EDIT_NEIGHBOR_SYNTAX);
        System.out.println("  " + Command.VALIDATE_MAP_SYNTAX);
        System.out.println("  " + Command.SAVE_MAP_SYNTAX);
        System.out.println("  " + Command.SHOW_MAP_SYNTAX);
        System.out.println("Type 'exit' to exit the game.");

        label:
        while (true) {
            System.out.print("map-editor> ");
            String[] l_inputString = scanner.nextLine().trim().toLowerCase().split("\\s+");
            String l_command = l_inputString[0];
            String[] l_args = Arrays.copyOfRange(l_inputString, 1, l_inputString.length);

            switch (l_command) {
                case "proceed":
                    System.out.println("Proceeding to the next phase...");
                    break label;
                case "exit":
                    System.out.println("Exiting the game...");
                    System.exit(0);
                    return;
                case Command.EDIT_CONTINENT:
                    countryController.handleEditContinentCommand(l_args);
                    break;
                case Command.EDIT_COUNTRY:
                    countryController.handleEditCountryCommand(l_args);
                    break;
                case Command.EDIT_NEIGHBOR:
                    countryController.handleEditNeighborCommand(l_args);
                    break;
                case Command.VALIDATE_MAP:
                    mapController.handleValidateMapCommand(l_args);
                    break;
                case Command.SAVE_MAP:
                    if (mapController.handleSaveMapCommand(l_args))
                        return;
                    break;
                case Command.SHOW_MAP:
                    gameState.printMap();
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        }
    }

    /**
     * This method is responsible for the game loop.
     */
    void issueOrdersPhase() {

        // playerFinishedOrders of a player is true if the player has finished issuing orders
        Map<String, Boolean> playerFinishedOrders = new HashMap<>();
        // initializing playerFinishedOrders to false for all players.
        for (Player player : gameState.getPlayers().values()) {
            playerFinishedOrders.put(player.getName(), false);
        }

        System.out.println("*-*-* ISSUE ORDERS PHASE *-*-*");
        System.out.println("Available commands: ");
        System.out.println("  " + Command.DEPLOY_SYNTAX);
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
                // only for build 1: player finishes ordering when all reinforcement is deployed.
                playerFinishedOrders.replace(player.getName(), player.getReinforcementPoll() == 0);
            }
        }
    }

    void executeOrdersPhase() {
        System.out.println("Executing orders...");
        boolean anOrderExecuted = true;
        while (anOrderExecuted) {
            anOrderExecuted = false;
            for (Player player : gameState.getPlayers().values()) {
                if (player.getOrders().isEmpty()) continue;

                Order order = player.nextOrder();
                order.execute();
                anOrderExecuted = true;
            }
        }
        //  print all the players and their countries and their armies
        for (Player player : gameState.getPlayers().values()) {
            System.out.println(player.getName() + " has the following countries: ");
            for (int countryId : player.getCountryIds()) {
                System.out.println(gameState.getCountries().get(countryId).getName() + " with " + gameState.getCountries().get(countryId).getNumberOfReinforcements() + " reinforcements.");
            }
        }
        
    }
}