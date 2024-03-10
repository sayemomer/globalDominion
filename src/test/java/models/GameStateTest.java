package models;

import controllers.CountryController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    private GameState gameState;
    private CountryController countryController;
    private Map<String, Player> players;

    private Map<Integer, Country> countries;
    private Map<Integer, Continent> continents;

    @BeforeEach
    void setUp() {
        gameState = new GameState();
        countryController = new CountryController(gameState);
        players = gameState.getPlayers();
        countries = gameState.getCountries();
        continents = gameState.getContinents();

        continents.put(1, new Continent(1, 3));
        continents.put(2, new Continent(2, 2));
        continents.put(3, new Continent(3, 4));

        Player p1 = new Player("farid");
        Player p2 = new Player("parsa");
        Player p3 = new Player("mahdie");

        players.put(p1.getName(), p1);
        players.put(p2.getName(), p2);
        players.put(p3.getName(), p3);


        countries.put(1, new Country(1, "Iran", 1));
        countries.put(2, new Country(2, "England", 2));
        countries.put(3, new Country(3, "France", 2));
        countries.put(4, new Country(4, "Iraq", 1));
        countries.put(5, new Country(5, "Canada", 3));



        p1.addCountry(countries.get(1));
        p2.addCountry(countries.get(2));
        p2.addCountry(countries.get(3));

    }

    @Test
    @DisplayName("reinforcements validation- check if bonus reinforcements are valid")
    void reinforcementBounsTest() {
        gameState.assignReinforcements();
        assertEquals(3, players.get("farid").getReinforcement());
        assertEquals(5, players.get("parsa").getReinforcement());
        assertEquals(3, players.get("mahdie").getReinforcement());
    }

    @Test
    @DisplayName("Continent validation- checks if returned list of continents owned by players is correct ")
    void getPlayerOwnedContinentsTest() {

        assertTrue(gameState.getPlayerOwnedContinents(players.get("farid")).isEmpty());
        assertTrue(gameState.getPlayerOwnedContinents(players.get("mahdie")).isEmpty());
        assertTrue(gameState.getPlayerOwnedContinents(players.get("parsa")).contains(2));
    }

    @Test
    @DisplayName("Country validation- checks if the list of country IDs for a given continent is correct ")
    void getCountryIDsInsideContinentTest() {
        ArrayList<Integer> c = gameState.getCountryIDsInsideContinent(2);
        assertTrue(c.contains(2));
        assertTrue(c.contains(3));
        assertTrue(c.size() == 2);
        c = gameState.getCountryIDsInsideContinent(1);
        assertTrue(c.contains(1));
        assertTrue(c.contains(4));
        assertTrue(c.size() == 2);
        c = gameState.getCountryIDsInsideContinent(3);
        assertTrue(c.contains(5));
        assertTrue(c.size() == 1);
    }

}