package controllers;

import config.Debug;
import models.GameState;
import models.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerController {
    private final GameState gameState;

    public PlayerController(GameState p_gameState) {
        gameState = p_gameState;
    }

    /**
     * This method is used to handle the player add and remove commands.
     *
     * @param p_args command arguments
     */
    public void handleGamePlayerCommand(String[] p_args) {
        Map<String, Player> players = gameState.getPlayers();
        try {
            if (p_args.length != 2)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.GAME_PLAYER_SYNTAX);

            String l_option = p_args[0].toLowerCase();
            String l_name = p_args[1].toLowerCase();

            if (l_option.equals(Command.ADD)) {
                if (players.containsKey(l_name))
                    throw new Exception("Player already exists.");

                Player newPlayer = new Player(l_name);
                players.put(newPlayer.getName(), newPlayer);
                Debug.log("Player " + newPlayer + " added.");
            }

            if (l_option.equals(Command.REMOVE)) {
                if (!players.containsKey(l_name))
                    throw new Exception("Player not found.");

                players.remove(l_name);
                Debug.log("Player " + l_name + " removed.");

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}