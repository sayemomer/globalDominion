package controllers;

import models.Country;
import models.GameState;
import models.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("player validation- doesn't add multiple players with the same name")
    void shouldNotAddMultiplePlayer() {
        String[] args = {"-add", "Mahdieh"};
        playerController.handleGamePlayerCommand(args);
        assertEquals(3, players.size());
    }

    @Test
    @DisplayName("player validation- remove player command doesn't remove a player that doesn't exist")
    void shouldNotRemoveNonExistentPlayer() {
        String[] args = {"-remove", "Amir"};
        playerController.handleGamePlayerCommand(args);
    }

    @Test
    @DisplayName("player validation- checks if the number of players reduce after removing a player")
    void shouldRemovePlayerCorrectly() {
        String[] args = {"-remove", "Mahdieh"};
        playerController.handleGamePlayerCommand(args);

        assertEquals(players.size(), 2);
    }
}