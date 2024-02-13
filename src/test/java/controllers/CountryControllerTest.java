package controllers;

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

    @BeforeEach
    void setUp() {
        gameState = new GameState();
        countryController = new CountryController(gameState);
        players = gameState.getPlayers();
        countries = gameState.getCountries();
    }


    @Test
    void handleAssignCountries() {
        players.add(new Player(1,"farid"));
        players.add(new Player(2, "parsa"));
        players.add(new Player(3,"Mahdie"));

        countries.put(1,new Country(1, "Iran",1));
        countries.put(2,new Country(2, "Canada",3));
        countries.put(3,new Country(3, "England",2));

        countryController.handleAssignCountriesCommand(new String[] {});

        for (Player player:
                players) {
            assertEquals(1, player.getCountries().size());
        }
    }
}