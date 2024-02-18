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
    private Map<String, Player> players;
    private Map<Integer, Country> countries;

    @BeforeEach
    void setUp() {
        gameState = new GameState();
        playerController = new PlayerController(gameState);
        players = gameState.getPlayers();
        countries = gameState.getCountries();

        Player p1 = new Player("farid"), p2 = new Player("parsa"), p3 = new Player("Mahdieh");
        players.put(p1.getName(), p1);
        players.put(p2.getName(), p2);
        players.put(p3.getName(), p3);

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
    void shouldNotRemoveNonExistentPlayer() {
        String[] args = {"-remove", "Amir"};
        playerController.handleGamePlayerCommand(args);
    }

    @Test
    void shouldRemovePlayerCorrectly() {
        String[] args = {"-remove", "Mahdieh"};
        playerController.handleGamePlayerCommand(args);

        assertEquals(players.size(), 2);
    }
}