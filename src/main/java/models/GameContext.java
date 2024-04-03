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

public class GameContext implements Serializable {
    GameState d_gameState;
    PlayerController d_playerController;
    MapController d_mapController;
    CountryController d_countryController;
    GameEngine d_gameEngine;

    public GameContext(GameState d_gameState, GameEngine d_gameEngine) {
        this.d_gameState = d_gameState;
        this.d_playerController = d_gameEngine.getPlayerController();
        this.d_mapController = d_gameEngine.getMapController();
        this.d_countryController = d_gameEngine.getCountryController();
        this.d_gameEngine = d_gameEngine;
    }

    /**
     * Constructor from file
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
     * @param p_mapPath
     * @param p_playerBehaviors
     * @param p_cycles
     */
    public GameContext(String p_mapPath, ArrayList<Class<? extends StrategyBehavior>> p_playerBehaviors, int p_cycles) {
        d_gameState = new GameState();
        d_playerController = new PlayerController(d_gameState);
        d_mapController = new MapController(d_gameState);
        d_countryController = new CountryController(d_gameState);
        d_gameEngine = new GameEngine(d_gameState, d_playerController, d_mapController, d_countryController, new OrderController(d_gameState, new Scanner(System.in)));
        setup(p_mapPath, p_playerBehaviors, p_cycles);
    }

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

    public void startGame() {
        ExecuteOrdersPhase.resetNumberOfExecutions();
        OrderController.resetStates(d_gameState, d_gameEngine);
        d_gameEngine.setGamePhase(new IssueDeployOrder(d_gameEngine));
        d_gameEngine.mainGameLoop();
    }

    /**
     * Save the game context to a file
     * @throws IOException
     */
    public void save(String p_filePath) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(p_filePath));
        d_gameEngine.setScanner(null);
        out.writeObject(this);
        out.close();
    }

    /**
     * loads this object from a file
     * @param p_filePath
     * @throws Exception
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

    public static void main(String[] args) {
        AppConfig.setTournamentMode(false);
        ArrayList<Class<? extends StrategyBehavior>> behaviors = new ArrayList<>();
        behaviors.add(AggressiveStrategyBehavior.class);
        behaviors.add(AggressiveStrategyBehavior.class);
        behaviors.add(AggressiveStrategyBehavior.class);

        GameContext gc = new GameContext("canada.map", behaviors, 50);
        try {
            gc.save("gamecolknkhntext.ser");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            GameContext gc2 = new GameContext("gamecontext.ser");
            gc2.startGame();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
