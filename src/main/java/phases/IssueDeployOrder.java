package phases;

import config.AppConfig;
import controllers.Command;
import models.Player;
import services.CustomPrint;

import java.util.HashMap;
import java.util.Map;

/**
 * issue deploy order
 */
public class IssueDeployOrder extends Phase {
    /**
     * constructor of IssueDeployOrder
     * @param p_gameEngine the game engine
     */
    public IssueDeployOrder(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * print available commands
     */
    @Override
    public void printAvailableCommands() {
        if (AppConfig.isTournamentMode()) {
            CustomPrint.println("*-*-* ISSUE DEPLOY ORDERS PHASE *-*-*");
        } else {
            CustomPrint.println("*-*-* ISSUE DEPLOY ORDERS PHASE *-*-*");
            CustomPrint.println("Players must deploy all their reinforcements in this phase.");
            CustomPrint.println("Available commands: ");
            CustomPrint.println("  " + Command.DEPLOY_SYNTAX);
            CustomPrint.println("Type 'exit' to exit the game.");
            CustomPrint.println("Players have the following reinforcements: " + d_gameEngine.getGameState().getPlayers().values().stream().map(player -> player.getName() + ": " + player.getReinforcementPoll()).reduce((a, b) -> a + ", " + b).orElse(""));
        }
    }

    /**
     * Runs the game loop, where each player takes turns issuing orders until all players have finished.
     * This method prints available commands, initializes the players' order status, and iterates over
     * players to allow them to issue orders. Once all players have finished ordering, it proceeds to
     * the execute orders phase.
     */
    @Override
    public boolean run() {
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
        return true;
    }

    /**
     * going to execute order phase.
     */
    public void goToExecuteOrdersPhase() {
        d_gameEngine.setGamePhase(new ExecuteDeployOrderPhase(d_gameEngine));
    }
}
