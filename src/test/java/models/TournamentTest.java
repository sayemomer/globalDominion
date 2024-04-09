package models;

import models.behaviors.StrategyBehavior;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TournamentTest {
    Tournament d_tournament;
    @BeforeEach
    public void setup() {

    }

    @Test
    public void testStartTournament() {
        ArrayList<Class<? extends StrategyBehavior>> behaviors = new ArrayList<>();
        behaviors.add(models.behaviors.AggressiveStrategyBehavior.class);
        behaviors.add(models.behaviors.AggressiveStrategyBehavior.class);

        ArrayList<String> mapPaths = new ArrayList<>();
        mapPaths.add("canada.map");
        Tournament tournament = new Tournament(behaviors, mapPaths, 20, 40);
        assertDoesNotThrow(tournament::startTournament);
    }
}
