package models.behaviors;

import controllers.OrderController;
import controllers.PlayerController;
import models.Continent;
import models.Country;
import models.GameState;
import models.Player;
import models.orders.AdvanceOrder;
import models.orders.DeployOrder;
import models.orders.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import phases.GameEngine;
import phases.IssueDeployOrderPhase;
import phases.IssueOrdersPhase;

import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RandomStrategyBehaviorTest {
    private GameState gameState;
    private GameEngine gameEngine;
    private static OrderController orderController;
    private PlayerController playerController;
    private Map<String, Player> players;
    private Map<Integer, Country> countries;
    private Map<Integer, Continent> continents;
    Random rand = new Random();

    @BeforeEach
    void setUp() {
        gameState = new GameState();
        playerController = new PlayerController(gameState);
        players = gameState.getPlayers();
        countries = gameState.getCountries();
        continents = gameState.getContinents();
        gameEngine = new GameEngine(gameState, null);



        Player p1 = new Player("mahdieh", gameState, RandomStrategyBehavior.class);
        p1.setReinforcement(3);
        Player p2 = new Player("parsa", gameState, RandomStrategyBehavior.class);
        p2.setReinforcement(3);

        players.put(p1.getName(), p1);
        players.put(p2.getName(), p2);


        // add continents and countries
        continents.put(1, new Continent(1, 3));

        countries.put(2, new Country(2, "CANADA", 1));
        countries.put(5, new Country(5, "SWEDEN", 1));
        p1.addCountry(countries.get(2));
        p1.addCountry(countries.get(5));

        countries.put(3, new Country(3, "USA", 1));
        countries.put(4, new Country(4, "France", 1));
        countries.put(6, new Country(6, "DENMARK", 1));
        p2.addCountry(countries.get(3));
        p2.addCountry(countries.get(4));
        p2.addCountry(countries.get(6));

        // add connections
        countries.get(2).addAdjacentCountry(3);
        countries.get(2).addAdjacentCountry(4);
        countries.get(2).addAdjacentCountry(6);
        countries.get(5).addAdjacentCountry(3);
        countries.get(5).addAdjacentCountry(4);
        countries.get(5).addAdjacentCountry(6);
        countries.get(4).addAdjacentCountry(3);
        countries.get(4).addAdjacentCountry(6);


        countries.get(2).setNumberOfReinforcements(4);
        countries.get(5).setNumberOfReinforcements(3);

        countries.get(3).setNumberOfReinforcements(1);
        countries.get(4).setNumberOfReinforcements(7);
        countries.get(6).setNumberOfReinforcements(5);

        orderController = new OrderController(gameState, null);

    }

    @Test
    @DisplayName("A player should deploy in Random country")
    public void shouldDeployInRandomCountry() {
        gameEngine.setGamePhase(new IssueDeployOrderPhase(gameEngine));
        Player player = players.get("mahdieh");
        player.issueOrder();
        assertEquals(1, player.getOrders().size());
        Order order = player.nextOrder();
        assertDoesNotThrow(() -> (DeployOrder) order);
        DeployOrder deployOrder = (DeployOrder) order;
        assertEquals(3, deployOrder.getNumReinforcements());
        assertTrue(player.getCountryIds().contains(deployOrder.getCountryId()));
    }

    @Test
    @DisplayName("player should attack randomly it's neighbor countries")
    public void shouldRandomAttack() {
        gameEngine.setGamePhase(new IssueOrdersPhase(gameEngine));
        Player player = players.get("mahdieh");
        player.issueOrder();
        assertEquals(1, player.getOrders().size());
        Order order = player.nextOrder();
        assertDoesNotThrow(() -> (AdvanceOrder) order);
        AdvanceOrder advanceOrder = (AdvanceOrder) order;
        assertEquals(3, advanceOrder.getNumReinforcements());
    }

    @Test
    @DisplayName("player should randomly getting one of it's countries")
    public void gettingRandomOwnsCountry() {
        gameEngine.setGamePhase(new IssueOrdersPhase(gameEngine));
        Player player = players.get("mahdieh");
        player.issueOrder();
        int randomPlayerCountryId = player.getCountryIds().get(rand.nextInt(player.getCountryIds().size()));
        assertTrue(player.getCountryIds().contains(randomPlayerCountryId));
    }

    @Test
    @DisplayName("player should randomly getting one of it's neighbor's countries")
    public void shouldChooseOneOfItsNeighborsCountries() {
        gameEngine.setGamePhase(new IssueOrdersPhase(gameEngine));
        Player player = players.get("mahdieh");
        player.issueOrder();
        int randomPlayerCountryId = player.getCountryIds().get(rand.nextInt(player.getCountryIds().size()));
        int randomAdjacentCountry = player.getCountries().get(randomPlayerCountryId).getAdjacentCountries().get(rand.nextInt(player.getCountries().get(randomPlayerCountryId).getAdjacentCountries().size()));
        assertTrue(player.getCountries().get(randomPlayerCountryId).getAdjacentCountries().contains(randomAdjacentCountry));
    }



}