package phases;

public class FinishPhase extends Phase {
    public FinishPhase(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void printAvailableCommands() {

    }

    @Override
    public void run() {
        System.out.println("Game is finished.");
        System.exit(0);
    }
}
