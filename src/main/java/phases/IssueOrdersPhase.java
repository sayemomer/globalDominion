package phases;

import controllers.Command;
import models.Player;

import java.util.HashMap;
import java.util.Map;

public class IssueOrdersPhase extends Phase {
    public IssueOrdersPhase(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void printAvailableCommands() {
        System.out.println("*-*-* ISSUE ORDERS PHASE *-*-*");
        System.out.println("Available commands: ");
        System.out.println("  " + Command.DEPLOY_SYNTAX);
        System.out.println("Type 'exit' to exit the game.");

    }

    @Override
    public void run() {
        // playerFinishedOrders of a player is true if the player has finished issuing orders
        Map<String, Boolean> playerFinishedOrders = new HashMap<>();
        // initializing playerFinishedOrders to false for all players.
        for (Player player : d_gameEngine.getGameState().getPlayers().values()) {
            playerFinishedOrders.put(player.getName(), false);
        }


        boolean aPlayerOrdered = true;
        // as long as a player has not finished ordering
        while (aPlayerOrdered) {
            aPlayerOrdered = false;
            for (Player player : d_gameEngine.getGameState().getPlayers().values()) {
                if (playerFinishedOrders.get(player.getName())) {
                    continue;
                }
                aPlayerOrdered = true;
                player.issueOrder();
                // only for build 1: player finishes ordering when all reinforcement is deployed.
                playerFinishedOrders.replace(player.getName(), player.getReinforcementPoll() == 0);
            }
        }
        goToExecuteOrdersPhase();
    }

    public void goToExecuteOrdersPhase() {
        d_gameEngine.setGamePhase(new ExecuteOrdersPhase(d_gameEngine));
    }
}
