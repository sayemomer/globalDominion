package controllers;

import models.GameContext;
import models.GameState;
import phases.GameEngine;

public class ContextController {
    /**
     * handles the loadgame command
     * @param p_args
     * @return true if game loaded successfully
     */
    public static boolean handleLoadGame(String[] p_args) {
        System.out.println("Loading game...");
        try {
            assert p_args.length == 1 : "Invalid number of arguments";
            GameContext l_gameContext = new GameContext(p_args[0]);
            l_gameContext.startGame();
            return true;
        } catch (Exception e) {
            System.out.println("Error loading game: " + e.getMessage());
        }
        return false;
    }

    public static void handleSaveGame(String[] p_args, GameState p_gameState, GameEngine p_gameEngine) {
        System.out.println("Saving game...");
        try {
            assert p_args.length == 1 : "Invalid number of arguments";
            GameContext l_gameContext = new GameContext(p_gameState, p_gameEngine);
            l_gameContext.save(p_args[0]);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

}
