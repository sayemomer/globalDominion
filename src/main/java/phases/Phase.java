package phases;

/**
 * Phase class to store the phase details
 */
public abstract class Phase {

    /**
     * Game engine
     */
    protected GameEngine d_gameEngine;

    /**
     * Constructor for the Phase class
     * @param p_gameEngine game engine
     */
    public Phase(GameEngine p_gameEngine) {
        d_gameEngine = p_gameEngine;
    }

    /**
     * Print available commands
     */
    public abstract void printAvailableCommands();

    /**
     * Run the phase ex. get user input, execute commands, ...
     * @return true if should continue
     */
    public abstract boolean run();

}
