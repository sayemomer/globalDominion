package controllers;

import models.Continent;
import models.Country;
import models.GameState;
import models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CountryControllerTest {
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

        Player p1 = new Player("farid"), p2 = new Player("parsa"), p3 = new Player("Mahdie");
        players.put(p1.getName(), p1);
        players.put(p2.getName(), p2);
        players.put(p3.getName(), p3);

        countries.put(1, new Country(1, "Iran", 1));
        countries.put(2, new Country(2, "England", 2));
        countries.put(3, new Country(3, "France", 2));
        countries.put(4, new Country(4, "Canada", 3));
        countries.put(5, new Country(5, "USA", 3));

        countries.get(1).addAdjacentCountry(2);
        countries.get(2).addAdjacentCountry(3);
        countries.get(3).addAdjacentCountry(1);
        countries.get(3).addAdjacentCountry(2);

    }


    @Test
    void assignedCountryShouldDeferByOneTest() {

        countryController.handleAssignCountriesCommand(new String[]{});
        int mx = 0;
        int mn = 10000;
        for (int i = 0; i < players.size(); i++) {
            mx = Math.max(mx, players.get(i).getCountries().size());
            mn = Math.min(mn, players.get(i).getCountries().size());

        }
        assertTrue(mx - mn < 2);

    }

    @Test
    void shouldNotAddCountryToNonExistingContinent() {
        countryController.handleEditCountryCommand(new String[]{"-add", "7", "7"});
        assertFalse(gameState.getCountries().containsKey(7));
    }

    @Test
    void shouldRemoveRelatedCountriesWhenContinentRemoved() {
        countryController.handleEditContinentCommand(new String[]{"-remove", "1"});
        assertFalse(gameState.getCountries().containsKey(1));
    }

    @Test
    void shouldRemoveRelatedConnectionsWhenCountryRemoved() {
        String[] args = {"-remove", "1"};
        countryController.handleEditCountryCommand(args);
        String[] args2 = {"-remove", "2"};
        countryController.handleEditCountryCommand(args2);
        assertFalse(gameState.getCountries().get(3).getAdjacentCountries().contains(1));
        assertFalse(gameState.getCountries().get(3).getAdjacentCountries().contains(2));
    }

}