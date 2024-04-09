package controllers;

import models.GameState;
import models.Tournament;
import models.behaviors.StrategyBehavior;
import services.CustomPrint;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The TournamentController class is responsible for handling the tournament command.
 */
public class TournamentController implements Serializable {

    /**
     * Constructor for TournamentController
     */
    public TournamentController() {
    }

    /**
     * Handles the tournament command.
     * @param p_args the arguments of the command.
     * @return the tournament object.
     */
    public Tournament handleTournamentCommand(String[] p_args) {

        ArrayList<Class<? extends StrategyBehavior>> l_behaviors = new ArrayList<>();
        ArrayList<String> l_mapPaths = new ArrayList<>();
        int l_numberOfGames = 0;
        int l_numberOfCycles = 0;

        if (p_args.length < 8) {
            CustomPrint.println("Invalid number arguments. Correct Syntax: \n\t" + Command.TOURNAMENT_SYNTAX);
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
                            CustomPrint.println("Problem loading the map " + l_mapPath);
                            return null;
                        }
                        l_mapPaths.add(l_mapPath);
                        i++;
                        argCounter++;
                    }
                    if (argCounter > 5 || argCounter < 1) {
                        CustomPrint.println("Invalid number of maps. Correct Syntax: \n\t" + Command.TOURNAMENT_SYNTAX);
                        return null;
                    }
                    CustomPrint.println("passed maps");
                } else if (p_args[i].equals(Command.TOURNAMENT_PLAYERS_OPTION)) {
                    i++;
                    int argCounter = 0;
                    while (!p_args[i].equals(Command.TOURNAMENT_GAMES_OPTION)) {
                        Class<? extends StrategyBehavior> behavior = StrategyBehavior.STR_TO_BEHAVIOR.get(p_args[i]);
                        if (behavior == null) {
                            CustomPrint.println("Invalid player behavior. Correct Syntax: \n\t" + Command.TOURNAMENT_SYNTAX);
                            return null;
                        }
                        l_behaviors.add(behavior);
                        i++;
                        argCounter++;
                    }
                    if (argCounter > 4 || argCounter < 2) {
                        CustomPrint.println("Invalid number of players. Correct Syntax: \n\t" + Command.TOURNAMENT_SYNTAX);
                        return null;
                    }
                    CustomPrint.println("passed players");
                } else if (p_args[i].equals(Command.TOURNAMENT_GAMES_OPTION)) {
                    i++;
                    l_numberOfGames = Integer.parseInt(p_args[i++]);
                    CustomPrint.println("passed games");

                } else if (p_args[i].equals(Command.TOURNAMENT_TURNS_OPTION)) {
                    i++;
                    l_numberOfCycles = Integer.parseInt(p_args[i++]);
                    CustomPrint.println("passed turns");
                } else {
                    CustomPrint.println("nothing found" + i);
                    throw new Exception();
                }
            }

            return new Tournament(l_behaviors, l_mapPaths, l_numberOfGames, l_numberOfCycles);

        } catch (Exception e) {
            CustomPrint.println("Invalid arguments. Correct Syntax: \n\t" + Command.TOURNAMENT_SYNTAX);
        }
        return null;
    }
}
