package services;

import models.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The GameGraphUtilsTest class is responsible for testing the GameGraphUtils class.
 */

public class GameGraphUtilsTest {

    private GameGraphUtils l_gameGraphUtils;

    /**
     * Sets up the context for the test.
     */

    @BeforeEach
    void setUp() {
        l_gameGraphUtils = new GameGraphUtils();
    }

    /**
     * Tests the isGraphConnected method.
     */
    @Test
    @DisplayName("Map validation – map is a connected graph")
    public void testIsGraphConnected() {
        // Set up a connected graph
        Map<Integer, Country> l_countries = new HashMap<>();
        // Country constructor: Country(int id, int country, List<Integer> adjacentCountries)
        l_countries.put(1, new Country(1, "Canada", 1, List.of(2, 3)));
        l_countries.put(2, new Country(2, "USA", 1, List.of(1, 3)));
        l_countries.put(3, new Country(3, "Mexico", 1, List.of(1, 2)));

        // test the graph is connected
        assertTrue(GameGraphUtils.isGraphConnected(l_countries), "Map is a connected graph");

        // Set up a disconnected graph
        l_countries = new HashMap<>();
        // Country constructor: Country(int id, int country, List<Integer> adjacentCountries)
        l_countries.put(1, new Country(1, "Canada", 1, List.of(2, 3)));
        l_countries.put(2, new Country(2, "USA", 1, List.of(1, 3)));
        l_countries.put(3, new Country(3, "Mexico", 1, List.of(1, 2)));
        l_countries.put(4, new Country(4, "Brazil", 1, List.of(5)));
        l_countries.put(5, new Country(5, "Argentina", 1, List.of(4)));

        // test the graph is not connected
        assertFalse(GameGraphUtils.isGraphConnected(l_countries), "Map is not a connected graph");
    }

    /**
     * Tests the isContinentConnected method. here we are testing the continent is a connected subgraph.
     */

    @Test
    @DisplayName("Continent validation – continent is a connected subgraph")
    public void testIsContinentConnected() {
        // Set up a connected graph
        Map<Integer, Country> l_countries = new HashMap<>();
        // Country constructor: Country(int id, int country, List<Integer> adjacentCountries)
        l_countries.put(1, new Country(1, "Canada", 1, List.of(2, 3)));
        l_countries.put(2, new Country(2, "USA", 1, List.of(1, 3)));
        l_countries.put(3, new Country(3, "Mexico", 1, List.of(1, 2)));

        assertTrue(GameGraphUtils.isContinentConnected(l_countries, 1), "Continent is a connected subgraph");

        // Set up a disconnected continent
        l_countries = new HashMap<>();
        // Country constructor: Country(int id, int country, List<Integer> adjacentCountries)
        l_countries.put(1, new Country(1, "Canada", 1, List.of(2)));
        l_countries.put(2, new Country(2, "USA", 1, List.of(1, 3)));
        l_countries.put(3, new Country(3, "Mexico", 1, List.of(2)));
        l_countries.put(4, new Country(4, "Brazil", 1, List.of(5)));
        l_countries.put(5, new Country(5, "Argentina", 1, List.of(4)));

        assertFalse(GameGraphUtils.isContinentConnected(l_countries, 1), "Continent is not a connected subgraph");
    }

    /**
     * test selfloop in the map
     */

    @Test
    @DisplayName("Map validation – map doesn't has self loop")
    public void testSelfLoop() {
        // Set up a connected graph
        Map<Integer, Country> l_countries = new HashMap<>();
        // Country constructor: Country(int id, int country, List<Integer> adjacentCountries)
        l_countries.put(1, new Country(1, "Canada", 1, List.of(1, 2, 3)));
        l_countries.put(2, new Country(2, "USA", 1, List.of(1, 3)));
        l_countries.put(3, new Country(3, "Mexico", 1, List.of(1, 2)));

        assertTrue(GameGraphUtils.hasSelfLoop(l_countries), "Map has self loop");
    }


}
