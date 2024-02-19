package controllers;

import config.Debug;
import models.GameState;
import models.Player;

import java.util.Map;

/**
 * The PlayerController class is responsible player related commands.
 */
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
        Map<String, Player> l_players = gameState.getPlayers();
        try {
            if (p_args.length != 2)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.GAME_PLAYER_SYNTAX);

            String l_option = p_args[0].toLowerCase();
            String l_name = p_args[1].toLowerCase();

            if (l_option.equals(Command.ADD)) {
                if (l_players.containsKey(l_name))
                    throw new Exception("Player already exists.");

                Player l_newPlayer = new Player(l_name);
                l_players.put(l_newPlayer.getName(), l_newPlayer);
                System.out.println("Player " + l_name + " added.");
                gameState.setActionDone(GameState.GameAction.PlAYERS_ADDED);
            } else if (l_option.equals(Command.REMOVE)) {
                if (!l_players.containsKey(l_name))
                    throw new Exception("Player not found.");

                System.out.println(("Player " + l_name + " removed."));
                if (gameState.getPlayers().isEmpty())
                    gameState.removeAction(GameState.GameAction.PlAYERS_ADDED);
            } else {
                throw new Exception("Invalid option. Correct Syntax: \n\t" + Command.GAME_PLAYER_SYNTAX);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}