package phases;

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

    Phase d_gamePhase;

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
        orderController = new OrderController(gameState, scanner);

        // print number of reinforcements for each player
        for (Player player : gameState.getPlayers().values()) {
            Debug.log(player.getName() + " has " + player.getReinforcementPoll() + " reinforcements.");

        }

    }

    /**
     * This is the constructor for the GameEngine class.
     *
     * @param p_gameState       The game state.
     * @param p_playerController The player controller.
     * @param p_mapController   The map controller.
     * @param p_countryController The country controller.
     * @param p_orderController The order controller.
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
        while (true)
            d_gamePhase.run();
    }


    public GameState getGameState() {
        return gameState;
    }

    public void setGamePhase(Phase p_gamePhase) {
        d_gamePhase = p_gamePhase;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public MapController getMapController() {
        return mapController;
    }

    public CountryController getCountryController() {
        return countryController;
    }

    public OrderController getOrderController() {
        return orderController;
    }


}