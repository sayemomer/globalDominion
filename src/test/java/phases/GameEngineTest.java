package phases;

import controllers.CountryController;
import controllers.MapController;
import controllers.OrderController;
import controllers.PlayerController;
import models.GameState;
import models.Player;
import models.orders.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class GameEngineTest {

    GameState gameState;
    PlayerController playerController;
    MapController mapController;
    CountryController countryController;
    OrderController orderController;

    GameEngine gameEngine;

    @BeforeEach
    public void setUp() {
        gameState = new GameState();
        playerController = new PlayerController(gameState);
        mapController = new MapController(gameState);
        countryController = new CountryController(gameState);
        orderController = new OrderController(gameState, null);
        gameEngine = new GameEngine(gameState, playerController, mapController, countryController, orderController);
    }

    @Test
    @DisplayName("Test to check conditions for entering issue order phase (when map us loaded)")
    public void shouldNotGoToIssueOrderWhenNoPlayersAreAdded() {
        StartupPhase startup = new StartupPhase(gameEngine);
        gameEngine.setGamePhase(startup);
        mapController.handleloadMapCommand(new String[]{"canada.map"});
        mapController.handleSaveMapCommand(new String[]{"canada.map"});
        startup.goToIssueOrdersPhase();
        assertEquals(startup, gameEngine.getGamePhase());
    }

    @Test
    @DisplayName("Test to check conditions for entering issue order phase (when players are added.)")
    public void shouldNotGoToIssueOrderWhenMapIsNotValid() {
        StartupPhase startup = new StartupPhase(gameEngine);
        playerController.handleGamePlayerCommand(new String[]{"-add", "player1"});
        gameEngine.setGamePhase(startup);
        startup.goToIssueOrdersPhase();
        assertEquals(startup, gameEngine.getGamePhase());
    }

    @AfterEach
    public void tearDown() {
        gameState = null;
        playerController = null;
        mapController = null;
        countryController = null;
        orderController = null;
        gameEngine = null;
    }
}
