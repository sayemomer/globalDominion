package phases;

import controllers.Command;
import models.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for the issue orders phase of the game.
 * It extends the Phase class.
 * It has the run method which is responsible for the issue orders phase of the game.
 * It has the printAvailableCommands method which is responsible for printing the available commands in the issue orders phase.
 * It has the goToExecuteOrdersPhase method which is responsible for changing the game phase to the execute orders phase.
 * It has the IssueOrdersPhase constructor which is responsible for creating an IssueOrdersPhase object.
 * It has the d_gameEngine attribute which is a GameEngine object.
 * It has the run method which is responsible for the issue orders phase of the game.
 */

public class IssueOrdersPhase extends Phase {

    /**
     * Constructor for the IssueOrdersPhase class
     * @param p_gameEngine game engine
     */
    public IssueOrdersPhase(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * Print available commands
     */
    @Override
    public void printAvailableCommands() {
        System.out.println("*-*-* ISSUE ORDERS PHASE *-*-*");
        System.out.println("Available commands: ");
        System.out.println("  " + Command.DEPLOY_SYNTAX);
        System.out.println("Type 'exit' to exit the game.");
    }

    /**
     * This method is responsible for the issue orders phase of the game.
     * gets user input and calls the appropriate method in the controllers.
     */

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

                // check if player is done ordering
                System.out.println("Want to continue? Type 'y' or 'n'");
                String l_inputString = d_gameEngine.getScanner().nextLine().trim().toLowerCase();

                if (l_inputString.equals("y")) {
                    playerFinishedOrders.replace(player.getName(), false);
                } else if (l_inputString.equals("n")) {
                    if (player.getReinforcementPoll() != 0) {
                        System.out.println("You still have " + player.getReinforcementPoll() + " reinforcements left. Please deploy them.");
                    } else {
                        playerFinishedOrders.replace(player.getName(), true);
                    }
                } else {
                    System.out.println("Invalid input. Please type 'y' or 'n'");
                }
            }
        }
        goToExecuteOrdersPhase();
    }

    /**
     * This method is responsible for changing the game phase to the execute orders phase.
     */

    public void goToExecuteOrdersPhase() {
        d_gameEngine.setGamePhase(new ExecuteOrdersPhase(d_gameEngine));
    }
}
