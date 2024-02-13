package controllers;

import models.Continent;
import models.Country;
import models.GameState;
import controllers.CountryController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class CountryControllerTest {

    private GameState gameState;
    private CountryController countryController;

    @BeforeEach
    void setUp() {
        gameState = new GameState();
        countryController = new CountryController(gameState);
        gameState.getContinents().put(1, new Continent(1, 2));
        // Set up test data
        Country country1 = new Country(1, "Country1", 1);
        Country country2 = new Country(2, "Country2", 1);
        gameState.getCountries().put(country1.getCountryId(), country1);
        gameState.getCountries().put(country2.getCountryId(), country2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void handleAssignCountriesCommand() {
    }

    @Test
    void shouldNotAddDuplicateConnection() {
        String[] args1 = {"-add", "1", "2"};
        countryController.handleEditNeighborCommand(args1);
        countryController.handleEditNeighborCommand(args1);
        assertEquals(gameState.getCountries().get(1).getAdjacentCountries().size(), 1);
    }

    @Test
    void handleEditNeighborCommand() {
        String[] args1 = {"-add", "1", "2"};
        countryController.handleEditNeighborCommand(args1);
        String[] args2 = {"-remove", "1", "2"};
        countryController.handleEditNeighborCommand(args2);
        countryController.handleEditNeighborCommand(args2);
        assertEquals(gameState.getCountries().get(2).getAdjacentCountries().size(), 0);


    }

    @Test
    void handleEditCountryCommand() {
    }

    @Test
    void handleEditContinentCommand() {
    }
}