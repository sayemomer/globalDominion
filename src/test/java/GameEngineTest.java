import controllers.CountryController;
import controllers.MapController;
import controllers.OrderController;
import controllers.PlayerController;
import models.GameState;
import models.Player;
import models.orders.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

public class GameEngineTest {

    GameState gameState;
    PlayerController playerController;
    MapController mapController;
    CountryController countryController;
    OrderController orderController;

    GameEngine gameEngine;

    @BeforeEach
    void setUp() {
        gameState = new GameState();
        playerController = new PlayerController(gameState);
        mapController = new MapController(gameState);
        countryController = new CountryController(gameState);
        orderController = new OrderController(gameState, null);
        gameEngine = new GameEngine(gameState, playerController, mapController, countryController, orderController);
    }

    @Test
    public void testExecuteOrdersAfterIssuing() throws IOException {

        //1.load a map
        mapController.handleloadMapCommand(new String[]{"test.map"});
        //2.add two players
        playerController.handleGamePlayerCommand(new String[]{"-add", "player1"});
        playerController.handleGamePlayerCommand(new String[]{"-add", "player2"});
        //3.assign countries
        countryController.handleAssignCountriesCommand(new String[]{});
        //4.assign reinforcement
        gameState.assignReinforcements();
        //get the assigned countries and player from getPlayerdetails and print it
        HashMap<String, Object> l_playerDetails = gameState.getPlayerDetails(gameState);
        Player player1 = gameState.getPlayers().get("player1");
        Player player2 = gameState.getPlayers().get("player2");
        //5.issue orders
        Order order = orderController.handleDeployOrderCommand(new String[]{ String.valueOf(player1.getCountryIds().get(0)), "2"}, gameState.getPlayers().get("player1"));
        player1.setOrder(order);
//      6.Execute orders
        Order nextOrder = player1.nextOrder();
        nextOrder.execute();
        //7. check if the order is executed
        assert (gameState.getCountries().get(player1.getCountryIds().get(0)).getArmies() == 2);
    }

    @AfterEach
    void tearDown() {
        gameState = null;
        playerController = null;
        mapController = null;
        countryController = null;
        orderController = null;
        gameEngine = null;
    }
}
