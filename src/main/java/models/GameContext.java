package models;

import config.AppConfig;
import controllers.CountryController;
import controllers.MapController;
import controllers.OrderController;
import controllers.PlayerController;
import models.behaviors.AggressiveStrategyBehavior;
import models.behaviors.StrategyBehavior;
import phases.ExecuteOrdersPhase;
import phases.GameEngine;
import phases.IssueDeployOrder;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * GameContext class to save and load the game
 * resposible for setting up and starting the game
 */
public class GameContext implements Serializable {
    /**
     * game state
     */
    GameState d_gameState;
    /**
     * player controller
     */
    PlayerController d_playerController;
    /**
     * map controller
     */
    MapController d_mapController;
    /**
     * country controller
     */
    CountryController d_countryController;
    /**
     * game engine
     */
    GameEngine d_gameEngine;

    /**
     * Constructor for GameContext
     * @param d_gameState game state
     * @param d_gameEngine game engine
     */
    public GameContext(GameState d_gameState, GameEngine d_gameEngine) {
        this.d_gameState = d_gameState;
        this.d_playerController = d_gameEngine.getPlayerController();
        this.d_mapController = d_gameEngine.getMapController();
        this.d_countryController = d_gameEngine.getCountryController();
        this.d_gameEngine = d_gameEngine;
    }

    /**
     * Constructor from file
     * @param p_filePath file path
     * @throws Exception if file not found
     */
    public GameContext(String p_filePath) throws Exception {
        loadFromFile(p_filePath);
    }

    /**
     * Constructor used for Single Mode
     */
    public GameContext() {
        d_gameState = new GameState();
        d_playerController = new PlayerController(d_gameState);
        d_mapController = new MapController(d_gameState);
        d_countryController = new CountryController(d_gameState);
        d_gameEngine = new GameEngine(d_gameState, d_playerController, d_mapController, d_countryController, new OrderController(d_gameState, new Scanner(System.in)));
    }

    /**
     * Constructor used for Tournament Mode
     * @param p_mapPath map path
     * @param p_playerBehaviors player behavior classes. all should be subclasses of StrategyBehavior.
     * @param p_cycles limit for the number of turns.
     */
    public GameContext(String p_mapPath, ArrayList<Class<? extends StrategyBehavior>> p_playerBehaviors, int p_cycles) {
        d_gameState = new GameState();
        d_playerController = new PlayerController(d_gameState);
        d_mapController = new MapController(d_gameState);
        d_countryController = new CountryController(d_gameState);
        d_gameEngine = new GameEngine(d_gameState, d_playerController, d_mapController, d_countryController, new OrderController(d_gameState, new Scanner(System.in)));
        setup(p_mapPath, p_playerBehaviors, p_cycles);
    }

    /**
     * Sets up the game using selected behavior classes used mainly in tournament mode
     * @param p_mapPath map path
     * @param p_playerBehaviors player behavior classes. all should be subclasses of StrategyBehavior.
     * @param p_limit limit for the number of turns.
     */
    private void setup(String p_mapPath, ArrayList<Class<? extends StrategyBehavior>> p_playerBehaviors, int p_limit) {
        boolean mapIsLoaded = d_mapController.handleloadMapCommand(new String[]{p_mapPath});
        if (!mapIsLoaded) {
            System.out.println("Problem loading the map " + p_mapPath);
            return;
        }

        int behaviorIndex = 1;
        for (Class<? extends StrategyBehavior> behavior : p_playerBehaviors) {
            Player player = new Player("p" + behaviorIndex + behavior.getName(), d_gameState, behavior);
            d_gameState.addPlayer(player);
            behaviorIndex++;
        }

        d_countryController.handleAssignCountriesCommand(new String[]{});
        ExecuteOrdersPhase.setLimitOfExecution(p_limit);
    }

    /**
     * Starts the game
     * resets the number of executions and sets the game phase to IssueDeployOrder
     */
    public void startGame() {
        ExecuteOrdersPhase.resetNumberOfExecutions();
        OrderController.resetStates(d_gameState, d_gameEngine);
        d_gameEngine.setGamePhase(new IssueDeployOrder(d_gameEngine));
        d_gameEngine.mainGameLoop();
    }

    /**
     * Save the game context to a file
     * @param p_filePath file path
     * @throws IOException if IO related exception occurs
     */
    public void save(String p_filePath) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(p_filePath));
        d_gameEngine.setScanner(null);
        out.writeObject(this);
        out.close();
    }

    /**
     * loads this object from a file
     * @param p_filePath file path
     * @throws Exception if IO related exception occurs
     */
    public void loadFromFile(String p_filePath) throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(p_filePath));
        GameContext gc = (GameContext) in.readObject();
        this.d_gameState = gc.d_gameState;
        this.d_playerController = gc.d_playerController;
        this.d_mapController = gc.d_mapController;
        this.d_countryController = gc.d_countryController;
        this.d_gameEngine = gc.d_gameEngine;
    }

    /**
     * toString method
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
