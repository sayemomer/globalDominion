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

import java.util.ArrayList;
import java.util.Scanner;

public class GameContext {
    GameState d_gameState;
    PlayerController d_playerController;
    MapController d_mapController;
    CountryController d_countryController;
    GameEngine d_gameEngine;

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

    public void saveContext() {

    }

    public static void main(String[] args) {
        AppConfig.setTournamentMode(true);
        ArrayList<Class<? extends StrategyBehavior>> behaviors = new ArrayList<>();
        behaviors.add(AggressiveStrategyBehavior.class);
        behaviors.add(AggressiveStrategyBehavior.class);
        behaviors.add(AggressiveStrategyBehavior.class);

        GameContext gc = new GameContext("canada.map", behaviors, 50);
        gc.startGame();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
