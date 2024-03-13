package phases;

public abstract class Phase {
    protected GameEngine d_gameEngine;

    public Phase(GameEngine p_gameEngine) {
        d_gameEngine = p_gameEngine;
    }

    /**
     * Print available commands
     */
    public abstract void printAvailableCommands();

    /**
     * Run the phase ex. get user input, execute commands, ...
     */
    public abstract void run();

}
