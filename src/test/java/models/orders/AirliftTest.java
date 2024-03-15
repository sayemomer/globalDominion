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


public class AirliftTest {

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
//        5 5 1

//        [borders]
//        1 2 3 4 5
//        2 3 4 1
//        3 4 1 2
//        4 1 2 3
//        5 1

        d_continents.put(1, new Continent(1, 1));
        d_continents.put(2, new Continent(2, 20));

        gameState.setContinents(d_continents);

        d_countries.put(1, new Country(1, "1", 1));
        d_countries.put(2, new Country(2, "2", 1));
        d_countries.put(3, new Country(3, "3", 2));
        d_countries.put(4, new Country(4, "4", 2));
        d_countries.put(5, new Country(5, "5", 1));

        gameState.setCountries(d_countries);

        d_countries.get(1).addAdjacentCountry(2);
        d_countries.get(1).addAdjacentCountry(3);
        d_countries.get(1).addAdjacentCountry(4);
        d_countries.get(1).addAdjacentCountry(5);

        d_countries.get(2).addAdjacentCountry(1);
        d_countries.get(2).addAdjacentCountry(3);
        d_countries.get(2).addAdjacentCountry(4);

        d_countries.get(3).addAdjacentCountry(1);
        d_countries.get(3).addAdjacentCountry(2);
        d_countries.get(3).addAdjacentCountry(4);

        d_countries.get(4).addAdjacentCountry(1);
        d_countries.get(4).addAdjacentCountry(2);
        d_countries.get(4).addAdjacentCountry(3);

        d_countries.get(5).addAdjacentCountry(1);

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
//        countryController.handleAssignCountriesCommand(new String[]{});

        //3. assign countries to players manually
        Player player1 = gameState.getPlayers().get("player1");
        Player player2 = gameState.getPlayers().get("player2");

        player1.addCountry(d_countries.get(1));
        player2.addCountry(d_countries.get(2));
        player1.addCountry(d_countries.get(3));
        player2.addCountry(d_countries.get(4));
        player1.addCountry(d_countries.get(5));

        //set assignedcountries true
        gameState.setActionDone(GameState.GameAction.COUNTRIES_ASSIGNED);

        //assignRandom reinforcement
        player1.setReinforcement(5);
        player2.setReinforcement(5);


        //4. assign reinforcement to players

        Order order = orderController.handleDeployOrderCommand(new String[]{ String.valueOf(player1.getCountryIds().get(1)), "2"}, gameState.getPlayers().get("player1"));
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
                new String[]{ String.valueOf(player1.getCountryIds().get(1)),
                        String.valueOf(player2.getCountryIds().get(1)),
                        "1"}, gameState.getPlayers().get("player1"));

        player1.setOrder(advanceOrder);


        //7. execute the advance order

        Order nextOrder3 = player1.nextOrder();
        nextOrder3.execute();

        //8. remove the card
        player1.removeAllCards();

        //9. Forcefully add the negotiate card
        Card card = Card.AIRLIFT;
        player1.forecefullyAddCard(card);
    }


    @Test
    @DisplayName("After successful airlift, the armies should be moved to the target country")
    public void testAirlift() {

        //10. issue a airlift order
        Order airliftOrder = orderController.handleAirliftOrderCommand(
                new String[]{ String.valueOf(gameState.getPlayers().get("player1").getCountryIds().get(1)),
                        String.valueOf(gameState.getPlayers().get("player1").getCountryIds().get(3)),
                        "1"}, gameState.getPlayers().get("player1"));

        gameState.getPlayers().get("player1").setOrder(airliftOrder);

        //11. execute the airlift order
        Order nextOrder4 = gameState.getPlayers().get("player1").nextOrder();
        nextOrder4.execute();

        //12. check if the airlift order is executed
        assertTrue (gameState.getCountries().get(gameState.getPlayers().get("player1").getCountryIds().get(3)).getArmies() == 1);

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
