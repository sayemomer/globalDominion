package models;

import config.AppConfig;
import models.behaviors.AggressiveStrategyBehavior;
import models.behaviors.StrategyBehavior;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class GameContextTest {

    GameContext gc;

    @BeforeEach
    public void setUp() {
        AppConfig.setTournamentMode(false);
        ArrayList<Class<? extends StrategyBehavior>> behaviors = new ArrayList<>();
        behaviors.add(AggressiveStrategyBehavior.class);
        behaviors.add(AggressiveStrategyBehavior.class);
        behaviors.add(AggressiveStrategyBehavior.class);
        gc = new GameContext("canada.map", behaviors, 50);
    }
    @Test
    public void shouldSaveLoadRun() {
        assertDoesNotThrow(()->gc.save("justfortest.ser"));
        assertDoesNotThrow(()->gc.loadFromFile("justfortest.ser"));
        assertDoesNotThrow(()->gc.startGame());
    }
}
