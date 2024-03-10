package controllers;

import models.Continent;
import models.Country;
import models.GameState;
import models.Player;
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
    private Map<Integer, Continent > continents;

    @BeforeEach
    void setUp() {
        gameState = new GameState();
        playerController = new PlayerController(gameState);
        players = gameState.getPlayers();
        countries = gameState.getCountries();
        continents = gameState.getContinents();

        Player p1 = new Player("farid");
        players.put(p1.getName(), p1);

        continents.put(1, new Continent(1, 3));

        countries.put(1, new Country(1, "Iran", 1));
        countries.put(4, new Country(4, "Iraq", 1));

        p1.addCountry(countries.get(1));
        orderController = new OrderController(gameState, new Scanner(System.in));


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Checks if ")
    void shouldDeployReinforcementsCorrectly(){

        String[] args = { "1", "3"} ;

        Order o = OrderController.handleDeployOrderCommand(args, players.get("farid"));
        assertNotNull(o);
        o.execute();
        assertEquals(3, countries.get(1).getNumberOfReinforcements());

    }
}