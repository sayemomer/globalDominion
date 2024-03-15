package models.orders;

import controllers.CountryController;
import controllers.MapController;
import controllers.OrderController;
import controllers.PlayerController;
import models.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.DisplayName;
import services.GameMapReader;

import java.util.Map;


public class DeplomacyTest {

    static GameState gameState;
    static MapController mapController;
    static GameMapReader gameMapReader;

    static CountryController countryController;
    static PlayerController playerController;

    static OrderController orderController;

    private static  Map<Integer, Continent> d_continents ;

    private static  Map<Integer, Country> d_countries ;

    @BeforeClass
    public static void setUp() {

        gameState = new GameState();
        mapController = new MapController(gameState);
        gameMapReader = new GameMapReader(gameState);
        countryController = new CountryController(gameState);
        playerController = new PlayerController(gameState);
        orderController = new OrderController(gameState, null);

        d_continents = gameMapReader.getContinents();
        d_countries = gameMapReader.getCountries();

        //create a map like this
//        [continents]
//        1 1
//        2 20

//        [countries]
//        1 1 1
//        2 2 1
//        3 3 2
//        4 4 2

//        [borders]
//        1 2 3 4
//        2 3 4 1
//        3 4 1 2
//        4 1 2 3

        d_continents.put(1, new Continent(1, 1));
        d_continents.put(2, new Continent(2, 20));

        gameState.setContinents(d_continents);

        d_countries.put(1, new Country(1, "1", 1));
        d_countries.put(2, new Country(2, "2", 1));
        d_countries.put(3, new Country(3, "3", 2));
        d_countries.put(4, new Country(4, "4", 2));

        gameState.setCountries(d_countries);

        d_countries.get(1).addAdjacentCountry(2);
        d_countries.get(1).addAdjacentCountry(3);
        d_countries.get(1).addAdjacentCountry(4);

        d_countries.get(2).addAdjacentCountry(1);
        d_countries.get(2).addAdjacentCountry(3);
        d_countries.get(2).addAdjacentCountry(4);

        d_countries.get(3).addAdjacentCountry(1);
        d_countries.get(3).addAdjacentCountry(2);
        d_countries.get(3).addAdjacentCountry(4);

        d_countries.get(4).addAdjacentCountry(1);
        d_countries.get(4).addAdjacentCountry(2);
        d_countries.get(4).addAdjacentCountry(3);

        //validate the map

        if (!gameMapReader.validateMap()) {
            gameState.removeAction(GameState.GameAction.VALID_MAP_LOADED);
            return;
        } else {
            gameState.setActionDone(GameState.GameAction.VALID_MAP_LOADED);
        }

        //2. add  two players
        playerController.handleGamePlayerCommand(new String[]{"-add", "player1"});
        playerController.handleGamePlayerCommand(new String[]{"-add", "player2"});

        //3. assign countries to players
        countryController.handleAssignCountriesCommand(new String[]{});

        //4. assign reinforcement to players
        Player player1 = gameState.getPlayers().get("player1");
        Player player2 = gameState.getPlayers().get("player2");

        Order order = orderController.handleDeployOrderCommand(new String[]{ String.valueOf(player1.getCountryIds().get(0)), "2"}, gameState.getPlayers().get("player1"));
        Order order2 = orderController.handleDeployOrderCommand(new String[]{ String.valueOf(player2.getCountryIds().get(0)), "2"}, gameState.getPlayers().get("player2"));
        player1.setOrder(order);
        player2.setOrder(order2);

        //5. execute the orders

        Order nextOrder = player1.nextOrder();
        nextOrder.execute();
        Order nextOrder2 = player2.nextOrder();
        nextOrder2.execute();

        //6. issue an advance order

        Order advanceOrder = orderController.handleAdvanceOrderCommand(
                new String[]{ String.valueOf(player1.getCountryIds().get(0)),
                        String.valueOf(player2.getCountryIds().get(1)),
                        "1"}, gameState.getPlayers().get("player1"));

        player1.setOrder(advanceOrder);


        //7. execute the advance order

        Order nextOrder3 = player1.nextOrder();
        nextOrder3.execute();

        //8. remove the card
        player1.removeAllCards();

        //9. Forcefully add the negotiate card
        Card card2 = Card.NEGOTIATE;
        player1.forecefullyAddCard(card2);

        //10. issue a negotiate order
        Order negotiateOrder = orderController.handleNegotiateOrderCommand(
                new String[]{"player2"}, gameState.getPlayers().get("player1"));

        player1.setOrder(negotiateOrder);

        //11. execute to negotiate order
        Order nextOrder4 = player1.nextOrder();
        nextOrder4.execute();



    }


    @Test
    @DisplayName("After negotiation, enemy country should not create an attack on the player's country")
    public void testDeplomacy() {


        assertNull(orderController.handleAdvanceOrderCommand(
                               new String[]{ String.valueOf(gameState.getPlayers().get("player2").getCountryIds().get(0)),
                                     String.valueOf(gameState.getPlayers().get("player1").getCountryIds().get(0)),
                                        "1"}, gameState.getPlayers().get("player2")));
    }

    @AfterClass
    public static void tearDown() {
        gameState = null;
        mapController = null;
        gameMapReader = null;
        countryController = null;
        playerController = null;
        orderController = null;
        d_continents = null;
        d_countries = null;

    }
}
