package controllers;

import models.GameState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

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
    @DisplayName("Check if the map loaded correctly")
    public void testMapLoaded() {
        // Test if the map is loaded correctly

        assertTrue(mapController.handleloadMapCommand(new String[]{"abc.map"}));

    }
}
