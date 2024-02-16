package models;

import controllers.CountryController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    private GameState gameState;
    private CountryController countryController;
    private ArrayList<Player> players;

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

        players.add(new Player(0, "farid"));
        players.add(new Player(1, "parsa"));
        players.add(new Player(2, "Mahdie"));


        countries.put(1, new Country(1, "Iran", 1));
        countries.put(2, new Country(2, "England", 2));
        countries.put(3, new Country(3, "France", 2));
        countries.put(4, new Country(4, "Iraq", 1));
        countries.put(5, new Country(5, "Canada", 3));


        players.get(0).addCountry(countries.get(1));
        players.get(1).addCountry(countries.get(2));
        players.get(1).addCountry(countries.get(3));


    }

    @Test
    void reinforcementBounsTest() {
        gameState.assignReinforcements();
        assertEquals(3, players.get(0).getReinforcement());
        assertEquals(5, players.get(1).getReinforcement());
        assertEquals(3, players.get(2).getReinforcement());

    }

    @Test
    void getPlayerOwnedContinentsTest() {
        for (int i : gameState.getPlayerOwnedContinents(players.get(0))) {
            System.out.println(i);
        }
        assertTrue(gameState.getPlayerOwnedContinents(players.get(0)).isEmpty());
        assertTrue(gameState.getPlayerOwnedContinents(players.get(2)).isEmpty());
        assertTrue(gameState.getPlayerOwnedContinents(players.get(1)).contains(2));
    }

    @Test
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