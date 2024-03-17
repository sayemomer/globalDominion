package phases;

import controllers.Command;
import models.Player;

import java.util.HashMap;
import java.util.Map;

public class IssueDeployOrder extends Phase {
    public IssueDeployOrder(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void printAvailableCommands() {
        System.out.println("*-*-* ISSUE DEPLOY ORDERS PHASE *-*-*");
        System.out.println("Players must deploy all their reinforcements in this phase.");
        System.out.println("Available commands: ");
        System.out.println("  " + Command.DEPLOY_SYNTAX);
        System.out.println("Type 'exit' to exit the game.");
        System.out.println("Players have the following reinforcements: " + d_gameEngine.getGameState().getPlayers().values().stream().map(player -> player.getName() + ": " + player.getReinforcementPoll()).reduce((a, b) -> a + ", " + b).orElse(""));
    }

    @Override
    public void run() {
        printAvailableCommands();
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
                playerFinishedOrders.replace(player.getName(), player.getReinforcementPoll() == 0);
            }
        }
        goToExecuteOrdersPhase();
    }

    public void goToExecuteOrdersPhase() {
        d_gameEngine.setGamePhase(new ExecuteDeployOrderPhase(d_gameEngine));
    }
}
