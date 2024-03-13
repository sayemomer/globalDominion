package phases;
import controllers.CountryController;
import controllers.MapController;
import controllers.OrderController;
import controllers.PlayerController;
import models.GameState;
import models.Player;
import models.orders.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class GameEngineTest {

    GameState gameState;
    PlayerController playerController;
    MapController mapController;
    CountryController countryController;
    OrderController orderController;

    GameEngine gameEngine;

    @Before
    public void setUp() {
        gameState = new GameState();
        playerController = new PlayerController(gameState);
        mapController = new MapController(gameState);
        countryController = new CountryController(gameState);
        orderController = new OrderController(gameState, null);
        gameEngine = new GameEngine(gameState, playerController, mapController, countryController, orderController);
    }

    @Test
    @DisplayName("Test to check if the issue order phase is working properly")
    public void testExecuteOrdersAfterIssuing() throws IOException {

        //1.load a map
        mapController.handleloadMapCommand(new String[]{"test.map"});
        //2.add two players
        playerController.handleGamePlayerCommand(new String[]{"-add", "player1"});
        playerController.handleGamePlayerCommand(new String[]{"-add", "player2"});
        //3.assign countries
        countryController.handleAssignCountriesCommand(new String[]{});
        //4.assign reinforcement
//        gameState.assignReinforcements();
        //get the assigned countries and player from getPlayerdetails and print it
        HashMap<String, Object> l_playerDetails = gameState.getPlayerDetails(gameState);
        Player player1 = gameState.getPlayers().get("player1");
        Player player2 = gameState.getPlayers().get("player2");
        //5.issue orders
        Order order = orderController.handleDeployOrderCommand(new String[]{ String.valueOf(player1.getCountryIds().get(0)), "2"}, gameState.getPlayers().get("player1"));
        Order order2 = orderController.handleDeployOrderCommand(new String[]{ String.valueOf(player2.getCountryIds().get(0)), "2"}, gameState.getPlayers().get("player2"));
        player1.setOrder(order);
        player2.setOrder(order2);
        //6.Execute orders
        Order nextOrder = player1.nextOrder();
        nextOrder.execute();
        Order nextOrder2 = player2.nextOrder();
        nextOrder2.execute();
        //7. check if the order is executed
        assertTrue (gameState.getCountries().get(player1.getCountryIds().get(0)).getArmies() == 2);
        assertTrue (gameState.getCountries().get(player2.getCountryIds().get(0)).getArmies() == 2);

        gameState.printMap();

        //8. issue an advance order

        Order order3 = orderController.handleAdvanceOrderCommand(
                new String[]{ String.valueOf(player1.getCountryIds().get(0)),
                        String.valueOf(player2.getCountryIds().get(1)),
                        "1"}, gameState.getPlayers().get("player1"));
        player1.setOrder(order3);
        //9. execute the advance order
        Order nextOrder3 = player1.nextOrder();
        nextOrder3.execute();
        gameState.printMap();

        //9. issue another advance order

        Order order4 = orderController.handleAdvanceOrderCommand(
                new String[]{ String.valueOf(player2.getCountryIds().get(0)),
                        String.valueOf(player1.getCountryIds().get(2)),
                        "1"}, gameState.getPlayers().get("player2"));
        player2.setOrder(order4);
        //10. execute the advance order
        Order nextOrder4 = player2.nextOrder();
        nextOrder4.execute();
        gameState.printMap();

        //11. handle the bomb order
        Order order5 = orderController.handleBombOrderCommand(
                new String[]{ String.valueOf(player1.getCountryIds().get(0))},
                gameState.getPlayers().get("player2")
        );
        player2.setOrder(order5);

        //12. execute the bomb order
        Order nextOrder5 = player2.nextOrder();
        nextOrder5.execute();
        gameState.printMap();
    }

    @After
    public void tearDown() {
        gameState = null;
        playerController = null;
        mapController = null;
        countryController = null;
        orderController = null;
        gameEngine = null;
    }
}
