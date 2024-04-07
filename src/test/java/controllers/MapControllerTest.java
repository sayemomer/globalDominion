package controllers;

import models.Continent;
import models.Country;
import models.GameState;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class MapControllerTest {

    private GameState gameState;
    private MapController mapController;

    @BeforeEach
    public void setUp() {

         gameState = new GameState();
         mapController = new MapController(gameState);
    }

    @Test
    //ignore this test case as it is failing
    @DisplayName("Check if the map loaded correctly")
    public void testMapLoaded() {
        // Test if the map is loaded correctly

        assertTrue(mapController.handleloadMapCommand(new String[]{"testing5.map"}));

    }

//    Test Edit Map feature

    @Test
    @DisplayName("Check if the map is edited correctly")
    public void testMapEdited() throws Exception {
        // Test if the map is edited correctly

        assertTrue(mapController.handleEditMapCommand(new String[]{"testing.map"}));

        //create a simple map using gamestate
        //add continent
        Continent continent1 = new Continent(1, 5);
        gameState.addContinent(continent1);

        //add country
        Country country1 = new Country(1, "India", 1);
        gameState.addCountry(country1);

        //add another country
        Country country2 = new Country(2, "Pakistan", 1);
        gameState.addCountry(country2);

        //add neighbor
        country1.addAdjacentCountry(country2.getCountryId());

        //add neighbor
        country2.addAdjacentCountry(country1.getCountryId());

        // set a file format to conquest

        gameState.setMapFormat("conquest");

        //save the map
        mapController.handleSaveMapCommand(new String[]{"testing.map"});


    }

    @AfterEach
    public void tearDown() {
        gameState = null;
        mapController = null;
        System.out.println("Deleting the file");
        File file = new File("src/main/resources/testing.map");
        file.delete();
    }
}
