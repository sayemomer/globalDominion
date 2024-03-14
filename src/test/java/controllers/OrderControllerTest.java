package controllers;

import models.Continent;
import models.Country;
import models.GameState;
import models.Player;
import models.orders.AdvanceOrder;
import models.orders.Order;
import controllers.OrderController.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Scanner;


public class OrderControllerTest {

    private GameState gameState;
    private static OrderController orderController;
    private PlayerController playerController;
    private Map<String, Player> players;
    private Map<Integer, Country> countries;
    private Map<Integer, Continent> continents;

    @BeforeEach
    void setUp() {
        gameState = new GameState();
        playerController = new PlayerController(gameState);
        players = gameState.getPlayers();
        countries = gameState.getCountries();
        continents = gameState.getContinents();

        // add players
        Player p1 = new Player("farid");
        p1.setReinforcement(3);
        Player p2 = new Player("parsa");
        p2.setReinforcement(3);
        Player p3 = new Player("mahdieh");
        p3.setReinforcement(3);

        players.put(p1.getName(), p1);
        players.put(p2.getName(), p2);
        players.put(p3.getName(), p3);

        // add continents
        continents.put(1, new Continent(1, 3));

        // add countries
        countries.put(1, new Country(1, "Iran", 1));
        countries.put(2, new Country(2, "CANADA", 1));
        countries.put(3, new Country(3, "USA", 1));
        countries.put(4, new Country(4, "Iraq", 1));

        // assign countries to players
        p1.addCountry(countries.get(1));
        p2.addCountry(countries.get(4));

        p3.addCountry(countries.get(2));
        p3.addCountry(countries.get(3));
        countries.get(2).addAdjacentCountry(3);

        countries.get(2).setNumberOfReinforcements(10);
        countries.get(3).setNumberOfReinforcements(6);
        orderController = new OrderController(gameState, new Scanner(System.in));
    }

    @AfterEach
    void tearDown() {
    }



    @Test
    @DisplayName("Checks if the deploy order is handled correctly")
    void shouldDeployReinforcementsCorrectly() {
        String[] args = {"1", "3"};
        Order o = OrderController.handleDeployOrderCommand(args, players.get("farid"));
        assertNotNull(o);
        o.execute();
        assertEquals(3, countries.get(1).getNumberOfReinforcements());
    }

    @Test
    @DisplayName("Checks handling of deploy order when player tries to deploy to a country not owned by them.")
    void shouldNotDeployReinforcementsToCountriesNotOwnedByPlayer() {
        String[] args = {"1", "2"};
        Order o = OrderController.handleDeployOrderCommand(args, players.get("parsa"));
        assertNull(o);
    }

    @Test
    @DisplayName("Checks handling of deploy order when player has not enough reinforcements.")
    void shouldNotDeployReinforcementsWhenPlayerHasNotEnough() {
        String[] args = {"1", "5"};
        Order o = OrderController.handleDeployOrderCommand(args, players.get("farid"));
        assertNull(o);
    }


    @Test
    @DisplayName("Checks if in advance order the number of reinforcement correctly changes")
    void shouldAdvanceReinforcementsCorrectly(){

        String[] args = { "2", "3", "3"} ;

        Order o = OrderController.handleAdvanceOrderCommand(args, players.get("mahdieh"));
        assertNotNull(o);
        o.execute();
        assertEquals(7, countries.get(2).getNumberOfReinforcements());
        assertEquals(9, countries.get(3).getNumberOfReinforcements());

    }

    @Test
    @DisplayName("checks whether it advance to a country that is not adjacent")
    void ShouldNotAdvanceToNotAdjacentCountry(){
        String[] args = {"2", "4", "4"};

        Order o = OrderController.handleAdvanceOrderCommand(args, players.get("mahdieh"));

    }

    @Test
    @DisplayName("Checks if in airlift order the number of reinforcement correctly changes")
    void shouldAirliftReinforcementsCorrectly(){
        String[] args = {"2","3","3"};

        Order o = OrderController.handleAirLiftOrderCommand(args, players.get("mahdieh"));
        assertNotNull(o);
        o.execute();
        assertEquals(7, countries.get(2).getNumberOfReinforcements());
        assertEquals(9, countries.get(3).getNumberOfReinforcements());

    }

    @Test
    @DisplayName("Checks handling of airlift order when player has not enough reinforcements.")
    void shouldNotAirliftReinforcementsWhenPlayerHasNotEnough() {
        String[] args = {"2", "3", "11"};
        Order o = OrderController.handleAirLiftOrderCommand(args, players.get("mahdieh"));
        assertNull(o);
    }

}
