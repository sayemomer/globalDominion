package phases;

import services.CustomPrint;

/**
 * This class is responsible for the finish phase of the game.
 * It extends the Phase class.
 * It has the run method which is responsible for the finish phase of the game.
 * It has the printAvailableCommands method which is responsible for printing the available commands in the finish phase.
 * It has the FinishPhase constructor which is responsible for creating a FinishPhase object.
 * It has the run method which is responsible for the finish phase of the game.
 */

public class FinishPhase extends Phase {

    /**
     * Constructor for the FinishPhase class
     * @param p_gameEngine game engine
     */
    public FinishPhase(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * Print available commands
     */

    @Override
    public void printAvailableCommands() {

    }

    /**
     * This method is responsible for the finish phase of the game.
     * gets user input and calls the appropriate method in the controllers.
     */

    @Override
    public boolean run() {
        CustomPrint.println("Game is finished.");
        return false;
    }
}
