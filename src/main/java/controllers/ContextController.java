package controllers;

import models.GameContext;
import models.GameState;
import phases.GameEngine;
import services.CustomPrint;

/**
 * The controller class for managing game context, including loading and saving games.
 */
public class ContextController {

    /**
     * Constructor for ContextController
     */
    public ContextController() {
    }

    /**
     * handles the loadgame command
     * @param p_args p_args an array containing the file path of the saved game
     * @return true if game loaded successfully
     */
    public static boolean handleLoadGame(String[] p_args) {
        CustomPrint.println("Loading game...");
        try {
            assert p_args.length == 1 : "Invalid number of arguments";
            GameContext l_gameContext = new GameContext(p_args[0]);
            l_gameContext.startGame();
            return true;
        } catch (Exception e) {
            CustomPrint.println("Error loading game: " + e.getMessage());
        }
        return false;
    }

    /**
     * Handles the savegame command.
     *
     * @param p_args an array containing the file path to save the game
     * @param p_gameState the current game state to be saved
     * @param p_gameEngine the game engine controlling the game
     */

    public static void handleSaveGame(String[] p_args, GameState p_gameState, GameEngine p_gameEngine) {
        CustomPrint.println("Saving game...");
        try {
            assert p_args.length == 1 : "Invalid number of arguments";
            GameContext l_gameContext = new GameContext(p_gameState, p_gameEngine);
            l_gameContext.save(p_args[0]);
        } catch (Exception e) {
            e.printStackTrace();
            CustomPrint.println("Error saving game: " + e.getMessage());
        }
    }

}
