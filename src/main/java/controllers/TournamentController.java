package controllers;

import models.GameState;
import models.Tournament;
import models.behaviors.StrategyBehavior;

import java.util.ArrayList;

/**
 * The TournamentController class handles tournament commands and generates a Tournament object accordingly.
 */
public class TournamentController {


    /**
     * Handles the tournament command and generates a Tournament object.
     *
     * @param p_args The arguments provided for the tournament command.
     * @return A Tournament object if successful; otherwise, null.
     */
    public Tournament handleTournamentCommand(String[] p_args) {

        ArrayList<Class<? extends StrategyBehavior>> l_behaviors = new ArrayList<>();
        ArrayList<String> l_mapPaths = new ArrayList<>();
        int l_numberOfGames = 0;
        int l_numberOfCycles = 0;

        if (p_args.length < 8) {
            System.out.println("Invalid number arguments. Correct Syntax: \n\t" + Command.TOURNAMENT_SYNTAX);
            return null;
        }
        try {
            int i = 0;
            while (i < p_args.length) {
                if (p_args[i].equals(Command.TOURNAMENT_MAPS_OPTION)) {
                    i++;
                    int argCounter = 0;
                    while (!p_args[i].equals(Command.TOURNAMENT_PLAYERS_OPTION)) {
                        String l_mapPath = p_args[i];
                        GameState l_gameState = new GameState();
                        MapController l_mapController = new MapController(l_gameState);
                        if (!l_mapController.handleloadMapCommand(new String[]{l_mapPath})) {
                            System.out.println("Problem loading the map " + l_mapPath);
                            return null;
                        }
                        l_mapPaths.add(l_mapPath);
                        i++;
                        argCounter++;
                    }
                    if (argCounter > 5 || argCounter < 1) {
                        System.out.println("Invalid number of maps. Correct Syntax: \n\t" + Command.TOURNAMENT_SYNTAX);
                        return null;
                    }
                    System.out.println("passed maps");
                } else if (p_args[i].equals(Command.TOURNAMENT_PLAYERS_OPTION)) {
                    i++;
                    int argCounter = 0;
                    while (!p_args[i].equals(Command.TOURNAMENT_GAMES_OPTION)) {
                        Class<? extends StrategyBehavior> behavior = StrategyBehavior.STR_TO_BEHAVIOR.get(p_args[i]);
                        if (behavior == null) {
                            System.out.println("Invalid player behavior. Correct Syntax: \n\t" + Command.TOURNAMENT_SYNTAX);
                            return null;
                        }
                        l_behaviors.add(behavior);
                        i++;
                        argCounter++;
                    }
                    if (argCounter > 4 || argCounter < 2) {
                        System.out.println("Invalid number of players. Correct Syntax: \n\t" + Command.TOURNAMENT_SYNTAX);
                        return null;
                    }
                    System.out.println("passed players");
                } else if (p_args[i].equals(Command.TOURNAMENT_GAMES_OPTION)) {
                    i++;
                    l_numberOfGames = Integer.parseInt(p_args[i++]);
                    System.out.println("passed games");

                } else if (p_args[i].equals(Command.TOURNAMENT_TURNS_OPTION)) {
                    i++;
                    l_numberOfCycles = Integer.parseInt(p_args[i++]);
                    System.out.println("passed turns");
                } else {
                    System.out.println("nothing found" + i);
                    throw new Exception();
                }
//                i++;
            }

            return new Tournament(l_behaviors, l_mapPaths, l_numberOfGames, l_numberOfCycles);

        } catch (Exception e) {
            System.out.println("Invalid arguments. Correct Syntax: \n\t" + Command.TOURNAMENT_SYNTAX);
        }
        return null;
    }
}
