package models.orders;

import controllers.OrderController;
import controllers.PlayerController;
import models.Continent;
import models.Country;
import models.GameState;
import models.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdvanceOrderTest {

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
        Player p1 = new Player("mahdieh");
        p1.setReinforcement(3);

        players.put(p1.getName(), p1);

        // add continents
        continents.put(1, new Continent(1, 3));

        // add countries
        countries.put(2, new Country(2, "CANADA", 1));
        countries.put(3, new Country(3, "USA", 1));
        countries.put(4, new Country(4, "Iraq", 1));

        // assign countries to players
        p1.addCountry(countries.get(4));

        p1.addCountry(countries.get(2));
        p1.addCountry(countries.get(3));
        countries.get(2).addAdjacentCountry(3);

        countries.get(2).setNumberOfReinforcements(10);
        countries.get(3).setNumberOfReinforcements(6);
        orderController = new OrderController(gameState, new Scanner(System.in));
    }

    @AfterEach
    void tearDown() {
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
}
