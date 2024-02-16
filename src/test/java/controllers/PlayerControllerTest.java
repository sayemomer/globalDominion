package controllers;

import models.Country;
import models.GameState;
import models.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerControllerTest {

    private GameState gameState;
    private PlayerController playerController;
    private ArrayList<Player> players;
    private Map<Integer, Country> countries;

    @BeforeEach
    void setUp() {
        gameState = new GameState();
        playerController = new PlayerController(gameState);
        players = gameState.getPlayers();
        countries = gameState.getCountries();

        players.add(new Player(1,"farid"));
        players.add(new Player(2, "parsa"));
        players.add(new Player(3,"Mahdieh"));

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldNotAddMultiplePlayer() {
        String[] args = {"-add", "Mahdieh"};
        playerController.handleGamePlayerCommand(args);
        assertEquals(3, players.size());
    }

    @Test
    void shouldNotRemoveNonExistentPlayer(){
        String[] args = {"-remove", "Amir"};
        playerController.handleGamePlayerCommand(args);
    }

    @Test
    void shouldRemovePlayerCorrectly(){
        String[] args = {"-remove", "Mahdieh"};
        playerController.handleGamePlayerCommand(args);

        assertEquals(players.size(), 2);
    }
}