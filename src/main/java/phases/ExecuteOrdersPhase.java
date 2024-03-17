package phases;

import models.Country;
import models.Player;
import models.orders.Order;

/**
 * This class is responsible for to execute orders phase of the game.
 * It extends the Phase class.
 * It has the run method which is responsible for to execute orders phase of the game.
 * It has the printAvailableCommands method which is responsible for printing the available commands in the execute orders phase.
 * It has the goToIssueOrdersPhase method which is responsible for changing the game phase to the issue orders phase.
 * It has the ExecuteOrdersPhase constructor which is responsible for creating an ExecuteOrdersPhase object.
 * It has the d_gameEngine attribute which is a GameEngine object.
 * It has the run method which is responsible for to execute orders phase of the game.
 */
public class ExecuteOrdersPhase extends Phase {

    /**
     * Constructor for the ExecuteOrdersPhase class
     * @param p_gameEngine game engine
     */

    public ExecuteOrdersPhase(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * Print available commands
     */

    @Override
    public void printAvailableCommands() {

    }

    /**
     * This method is responsible for the execute orders phase of the game.
     * gets user input and calls the appropriate method in the controllers.
     */

    @Override
    public void run() {
        System.out.println("Executing orders...");
        boolean anOrderExecuted = true;
        // continues until no orders are left to execute
        while (anOrderExecuted) {
            anOrderExecuted = false;
            for (Player player : d_gameEngine.getGameState().getPlayers().values()) {
                if (player.getOrders().isEmpty()) continue;

                Order order = player.nextOrder();
                order.execute();
                anOrderExecuted = true;
            }
        }
        //  print all the players and their countries and their armies
        for (Player player : d_gameEngine.getGameState().getPlayers().values()) {
            System.out.println(player.getName() + " has the following countries: ");
            for (int countryId : player.getCountryIds()) {
                System.out.println(d_gameEngine.getGameState().getCountries().get(countryId).getName() + " with " + d_gameEngine.getGameState().getCountries().get(countryId).getNumberOfReinforcements() + " reinforcements.");
            }
        }

        goToIssueOrdersPhase();
    }

    /**
     * This method is responsible for changing the game phase to the issue orders phase.
     * @throws Exception if the player has won
     */

    public void resumeOrThrow() throws Exception {
        for (Player player : d_gameEngine.getGameState().getPlayers().values()) {
            if (player.getCountryIds().size() == d_gameEngine.getGameState().getCountries().size())
                throw new Exception("Player " + player + " has won.");
        }
    }

    /**
     * This method is responsible for changing the game phase to the issue orders phase.
     */

    public void goToFinishPhase() {
        d_gameEngine.setGamePhase(new FinishPhase(d_gameEngine));
    }

    /**
     * This method is responsible for changing the game phase to the issue orders phase.
     */

    public void goToIssueOrdersPhase() {
        try {
            resumeOrThrow();
            d_gameEngine.setGamePhase(new IssueOrdersPhase(d_gameEngine));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            goToFinishPhase();
        }
    }
}
