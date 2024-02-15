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
    private ArrayList<Player> players;

    private Map<Integer, Country> countries;
    private Map<Integer, Continent> continents;

    @BeforeEach
    void setUp() {
        gameState = new GameState();
        countryController = new CountryController(gameState);
        players = gameState.getPlayers();
        countries = gameState.getCountries();

        players.add(new Player(1,"farid"));
        players.add(new Player(2, "parsa"));
        players.add(new Player(3,"Mahdie"));

        countries.put(1,new Country(1, "Iran",1));
        countries.put(2,new Country(2, "Canada",3));
        countries.put(3,new Country(3, "England",2));

        countries.get(1).addAdjacentCountry(2);
        countries.get(2).addAdjacentCountry(3);
        countries.get(3).addAdjacentCountry(1);

    }


    @Test
    void handleAssignCountries() {

        countryController.handleAssignCountriesCommand(new String[] {});

        for (Player player:
                players) {
            assertEquals(1, player.getCountries().size());
        }
    }

    @Test
    void shouldNotAddCountryToNonExistingContinent() {
        countryController.handleEditCountryCommand(new String[] {"-add", "7", "7"});
        assertFalse(gameState.getCountries().containsKey(7));
    }

    @Test
    void shouldRemoveRelatedCountriesWhenContinentRemoved() {
        countryController.handleEditContinentCommand(new String[] {"-remove", "1"});
        assertFalse(gameState.getCountries().containsKey(1));
    }

    @Test
    void shouldRemoveRelatedConnectionsWhenCountryRemoved() {
        String[] args = {"-remove", "1"};
        countryController.handleEditCountryCommand(args);
        assertFalse(gameState.getCountries().get(3).getAdjacentCountries().contains(1));
    }

}