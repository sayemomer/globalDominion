package phases;

import config.AppConfig;
import config.Debug;
import controllers.*;
import models.GameState;
import models.Player;
import models.orders.Order;

import java.io.Serializable;
import java.util.*;

/**
 * The GameEngine class is responsible for handling the game loop and the game phases.
 * It utilizes the PlayerController and MapController classes to handle the game phases.
 * It also utilizes the OrderController class to handle the orders.
 * It also utilizes the GameState class to keep track of the game state.
 * It also utilizes the Scanner class to get user input.
 * It also utilizes the Phase class to store the phase details.
 * It also utilizes the StartupPhase class to handle the startup phase of the game.
 * It also utilizes the IssueOrdersPhase class to handle the issue orders phase of the game.
 * It also utilizes the ExecuteOrdersPhase class to handle the execute orders phase of the game.
 * It also utilizes the Player class to keep track of the player's state.
 * It also utilizes the Map class to keep track of the map state.
 * It also utilizes the Country class to keep track of the country's state.
 * It also utilizes the Order class to keep track of the order's state.
 * It also utilizes the GameState class to keep track of the game state.
 * It also utilizes the AppConfig class to keep track of the application configuration.
 * It also utilizes the Debug class to print debug messages.
 * It also utilizes the Command class to store the command details.
 * It also utilizes the Phase class to store the phase details.
 *
 */

public class GameEngine implements Serializable {
    /**
     * The scanner object.
     */
    Scanner scanner;
    /**
     * The player controller object.
     */
    PlayerController playerController;
    /**
     * The map controller object.
     */
    MapController mapController;
    /**
     * The country controller object.
     */
    CountryController countryController;
    /**
     * The order controller object.
     */
    OrderController orderController;
    /**
     * The game state object.
     */
    GameState gameState;
    /**
     * The phase object.
     */
    Phase d_gamePhase;
    /**
     * The number of cycles.
     */
    int d_numberOfCycles = -1;

    /**
     * This is the constructor for the GameEngine class.
     *
     * @param p_gameState The game state.
     * @param p_scanner   The scanner object.
     */
    public GameEngine(GameState p_gameState, Scanner p_scanner) {
        d_gamePhase = new StartupPhase(this);
        gameState = p_gameState;
        scanner = p_scanner;
        playerController = new PlayerController(gameState);
        mapController = new MapController(gameState);
        countryController = new CountryController(gameState);
        orderController = new OrderController(this);
    }

    /**
     * This is the constructor for the GameEngine class.
     *
     * @param p_gameState         The game state.
     * @param p_playerController  The player controller.
     * @param p_mapController     The map controller.
     * @param p_countryController The country controller.
     * @param p_orderController   The order controller.
     */
    public GameEngine(GameState p_gameState, PlayerController p_playerController, MapController p_mapController, CountryController p_countryController, OrderController p_orderController) {
        d_gamePhase = new StartupPhase(this);
        gameState = p_gameState;
        playerController = p_playerController;
        mapController = p_mapController;
        countryController = p_countryController;
        orderController = p_orderController;
    }

    /**
     * This method is the main game loop.
     * It calls the startUpPhase, issueOrdersPhase, and executeOrdersPhase methods.
     */
    public void mainGameLoop() {
        boolean shouldContinue = true;
        while (shouldContinue) {
            shouldContinue = d_gamePhase.run();
        }
    }

    public void setNumberOfCycles(int p_cycles) {
        d_numberOfCycles = p_cycles;
    }

    public void setScanner(Scanner p_scanner) {
        scanner = p_scanner;
    }

    /**
     * This method sets the game phase to the startup phase.
     * @return The startup phase.
     */

    public GameState getGameState() {
        return gameState;
    }

    /**
     * This method sets the game phase.
     *
     * @param p_gamePhase The game phase.
     */
    public void setGamePhase(Phase p_gamePhase) {
        d_gamePhase = p_gamePhase;
    }

    /**
     * This method gets the game phase.
     *
     * @return The game phase.
     */
    public Phase getGamePhase() {
        return d_gamePhase;
    }

    /**
     * This method gets the scanner.
     *
     * @return The scanner.
     */

    public Scanner getScanner() {
        return scanner;
    }

    /**
     * This method gets the player controller.
     *
     * @return The player controller.
     */

    public PlayerController getPlayerController() {
        return playerController;
    }

    /**
     * This method gets the map controller.
     *
     * @return The map controller.
     */

    public MapController getMapController() {
        return mapController;
    }

    /**
     * This method gets the country controller.
     *
     * @return The country controller.
     */

    public CountryController getCountryController() {
        return countryController;
    }

    /**
     * This method gets the order controller.
     *
     * @return The order controller.
     */

    public OrderController getOrderController() {
        return orderController;
    }


}