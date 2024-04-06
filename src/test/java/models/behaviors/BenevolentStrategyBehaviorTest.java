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
import phases.IssueDeployOrder;
import phases.IssueOrdersPhase;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BenevolentStrategyBehaviorTest {
    private GameState gameState;
    private GameEngine gameEngine;
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
        gameEngine = new GameEngine(gameState, null);



        Player p1 = new Player("mahdieh", gameState, BenevolentStrategyBehavior.class);
        p1.setReinforcement(3);
        Player p2 = new Player("parsa", gameState, BenevolentStrategyBehavior.class);
        p2.setReinforcement(3);

        players.put(p1.getName(), p1);
        players.put(p2.getName(), p2);
        p1.setReinforcement(3);


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
        countries.get(2).addAdjacentCountry(5);
        countries.get(5).addAdjacentCountry(3);
        countries.get(5).addAdjacentCountry(4);
        countries.get(5).addAdjacentCountry(6);
        countries.get(5).addAdjacentCountry(2);
        countries.get(4).addAdjacentCountry(3);
        countries.get(4).addAdjacentCountry(6);


        countries.get(2).setNumberOfReinforcements(4);
        countries.get(5).setNumberOfReinforcements(2);

        countries.get(3).setNumberOfReinforcements(1);
        countries.get(4).setNumberOfReinforcements(7);
        countries.get(6).setNumberOfReinforcements(5);

        orderController = new OrderController(gameState, null);

    }
    @Test
    @DisplayName("Benevolent player's reinforcement should be deployed in weakest country")
    public void shouldDeployInWeakestCountry() {
        gameEngine.setGamePhase(new IssueDeployOrder(gameEngine));
        Player player = players.get("mahdieh");
        player.issueOrder();
        assertEquals(1, player.getOrders().size());
        Order order = player.nextOrder();
        order.execute();
        assertEquals(2+player.getReinforcement(), countries.get(5).getNumberOfReinforcements());
    }

    @Test
    @DisplayName("should move armies of strongest neighbor to weakest country")
    public void shouldMoveFromStrongestNeighborToWeakest(){
        gameEngine.setGamePhase(new IssueOrdersPhase(gameEngine));
        Player player = players.get("mahdieh");
        player.issueOrder();
        Order order = player.nextOrder();
        assertDoesNotThrow(() -> (AdvanceOrder) order);
//        assertEquals(1, player.getOrders().size());
        AdvanceOrder advanceOrder = (AdvanceOrder) order;
        advanceOrder.execute();
        assertEquals(6, countries.get(5).getNumberOfReinforcements());
        assertEquals(0, countries.get(2).getNumberOfReinforcements());

    }
}
