package models.orders;

import controllers.CountryController;
import controllers.MapController;
import controllers.OrderController;
import controllers.PlayerController;
import models.*;

import static org.junit.Assert.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.GameMapReader;

import java.util.Map;


public class BlockadeOrderTest {

    static GameState gameState;
    static MapController mapController;
    static GameMapReader gameMapReader;

    static CountryController countryController;
    static PlayerController playerController;

    static OrderController orderController;

    private static  Map<Integer, Continent> d_continents ;

    private static  Map<Integer, Country> d_countries ;

    @BeforeEach
    public void setUp() {

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
        Card card = Card.BLOCKADE;
        player1.forecefullyAddCard(card);
    }


    @Test
    @DisplayName("After successful blockade order, the country should be neutral and the armies should be thrice the original armies")
    public void testBlockadeOrder() {

        //10. issue a blockade order

        //get the initial armies
        int initialArmies = gameState.getCountries().get(1).getArmies();
        Order blockadeOrder = orderController.handleBlockadeOrderCommand(
                new String[]{ String.valueOf(gameState.getPlayers().get("player1").getCountryIds().get(1))}, gameState.getPlayers().get("player1"));


        gameState.getPlayers().get("player1").setOrder(blockadeOrder);

        //11. execute the airlift order
        Order nextOrder4 = gameState.getPlayers().get("player1").nextOrder();
        nextOrder4.execute();

        gameState.printMap();

        Player player = gameState.getPlayers().get("neutral");

        //12. check if the blockade order is executed
        assertTrue (gameState.getCountries().get(1).getArmies() == initialArmies * 3);

    }

    @AfterEach
    public void tearDown() {
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
