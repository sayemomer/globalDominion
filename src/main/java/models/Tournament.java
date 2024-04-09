package models;

import models.behaviors.StrategyBehavior;

import java.util.ArrayList;

/**
 * The Tournament model contains the information about the tournament.
 */
public class Tournament {
    ArrayList<String> d_mapPaths;
    ArrayList<Class<? extends StrategyBehavior>> d_behaviors;
    int d_numberOfGames;
    int d_numberOfTurns;

    /**
     * Constructor for Tournament
     * @param p_behaviors The list of behavior classes. all should be subclasses of StrategyBehavior
     * @param p_mapPaths The list of map paths.
     * @param p_numberOfGames The number of games to be played.
     * @param p_numberOfTurns The number of turns per game.
     */
    public Tournament(ArrayList<Class<? extends StrategyBehavior>> p_behaviors, ArrayList<String> p_mapPaths, int p_numberOfGames, int p_numberOfTurns) {
        d_behaviors = p_behaviors;
        d_mapPaths = p_mapPaths;
        d_numberOfGames = p_numberOfGames;
        d_numberOfTurns = p_numberOfTurns;

    }

    /**
     * Starts the tournament.
     */
    public void startTournament() {
        for (String mapPath : d_mapPaths) {
                for (int i = 0; i < d_numberOfGames; i++) {
                    System.out.println("NEW GAME: mapPath: " + mapPath + ", "+  "game number: " + i);
                    GameContext gameContext = new GameContext(mapPath, d_behaviors, d_numberOfTurns);
                    gameContext.startGame();
                }

        }
    }

}
