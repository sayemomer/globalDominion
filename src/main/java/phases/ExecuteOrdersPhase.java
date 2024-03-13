package phases;

import models.Player;
import models.orders.Order;

public class ExecuteOrdersPhase extends Phase {

    public ExecuteOrdersPhase(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void printAvailableCommands() {

    }

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

    public void resumeOrThrow() throws Exception {
        // TODO: must check if the game is finished; if so, throw an exception.
        throw new Exception("Game is finished.");
    }

    public void goToFinishPhase() {
        d_gameEngine.setGamePhase(new FinishPhase(d_gameEngine));
    }

    public void goToIssueOrdersPhase() {
        try {
            resumeOrThrow();
            d_gameEngine.setGamePhase(new IssueOrdersPhase(d_gameEngine));
        } catch (Exception e) {
            goToFinishPhase();
        }
    }
}