package models;

import models.behaviors.StrategyBehavior;

import java.util.ArrayList;

public class Tournament {
    ArrayList<String> d_mapPaths;
    ArrayList<Class<? extends StrategyBehavior>> d_behaviors;
    int d_numberOfGames;
    int d_numberOfCycles;

    public Tournament(ArrayList<Class<? extends StrategyBehavior>> p_behaviors, ArrayList<String> p_mapPaths, int p_numberOfGames, int p_numberOfCycles) {
        d_behaviors = p_behaviors;
        d_mapPaths = p_mapPaths;
        d_numberOfGames = p_numberOfGames;
        d_numberOfCycles = p_numberOfCycles;

    }

    public void startTournament() {
        for (String mapPath : d_mapPaths) {
                for (int i = 0; i < d_numberOfGames; i++) {
                    System.out.println("NEW GAME: mapPath: " + mapPath + ", "+  "game number: " + i);
                    GameContext gameContext = new GameContext(mapPath, d_behaviors, d_numberOfGames);
                    gameContext.startGame();
                }

        }
    }

    public static void main(String[] args) {
        ArrayList<Class<? extends StrategyBehavior>> behaviors = new ArrayList<>();
        behaviors.add(models.behaviors.AggressiveStrategyBehavior.class);
        behaviors.add(models.behaviors.AggressiveStrategyBehavior.class);
        
        ArrayList<String> mapPaths = new ArrayList<>();
        mapPaths.add("canada.map");
        Tournament tournament = new Tournament(behaviors, mapPaths, 20, 40);
        tournament.startTournament();
    }

}
