package controllers;

import models.GameState;
import models.Player;

import java.util.ArrayList;

public class PlayerController {
    private final GameState gameState;

    public PlayerController(GameState p_gameState) {
        gameState = p_gameState;
    }

    /**
     * This method is used to handle the player add and remove commands.
     * @param p_args command arguments
     */
    public void handleGamePlayerCommand(String[] p_args) {
        ArrayList<Player> players = gameState.getPlayers();
        try {
            if (p_args.length != 2)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.GAME_PLAYER_SYNTAX);

            String l_option = p_args[0].toLowerCase();
            String l_name = p_args[1].toLowerCase();

            if (l_option.equals(Command.ADD)) {
                for (Player player : players)
                    if (player.getName().equals(l_name))
                        throw new Exception("Player already exists.");

                players.add(new Player(players.size(), l_name));
            }

            if (l_option.equals(Command.REMOVE)) {
                boolean found = false;
                for (Player player : players) {
                    if (player.getName().equals(l_name)) {
                        players.remove(player);
                        found = true;
                        break;
                    }
                }
                if (!found)
                    throw new Exception("Player not found.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}