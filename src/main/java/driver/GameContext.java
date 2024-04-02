package driver;

import config.AppConfig;
import controllers.CountryController;
import controllers.MapController;
import controllers.OrderController;
import controllers.PlayerController;
import models.GameState;
import models.Player;
import models.behaviors.AggressiveStrategyBehavior;
import models.behaviors.StrategyBehavior;
import phases.ExecuteOrdersPhase;
import phases.GameEngine;
import phases.IssueDeployOrder;

import java.util.Scanner;

public class GameContext {
    GameState d_gameState;
    PlayerController d_playerController;
    MapController d_mapController;
    CountryController d_countryController;
    GameEngine d_gameEngine;
    Player p1, p2;

    GameContext(String p_mapPath, Class<? extends StrategyBehavior> p_player1Behavior, Class<? extends StrategyBehavior> p_player2Behavior, int p_cycles) {
        d_gameState = new GameState();
        d_playerController = new PlayerController(d_gameState);
        d_mapController = new MapController(d_gameState);
        d_countryController = new CountryController(d_gameState);
        d_gameEngine = new GameEngine(d_gameState, d_playerController, d_mapController, d_countryController, new OrderController(d_gameState, null));
        setup(p_mapPath, p_player1Behavior, p_player2Behavior, p_cycles);
    }

    private void setup(String p_mapPath, Class<? extends StrategyBehavior> p_player1Behavior, Class<? extends StrategyBehavior> p_player2Behavior, int p_limit) {
        boolean mapIsLoaded = d_mapController.handleloadMapCommand(new String[]{p_mapPath});
        if (!mapIsLoaded) {
            System.out.println("Problem loading the map " + p_mapPath);
            return;
        }

        p1 = new Player("p1" + p_player1Behavior.getName(), d_gameState, p_player1Behavior);
        p2 = new Player("p2" + p_player2Behavior.getName(), d_gameState, p_player2Behavior);

        d_gameState.addPlayer(p1);
        d_gameState.addPlayer(p2);

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
        GameContext gc = new GameContext("canada.map", AggressiveStrategyBehavior.class, AggressiveStrategyBehavior.class, 5);
        gc.startGame();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
