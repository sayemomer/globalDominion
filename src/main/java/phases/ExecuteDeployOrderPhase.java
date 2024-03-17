package phases;

public class ExecuteDeployOrderPhase extends ExecuteOrdersPhase {
    public ExecuteDeployOrderPhase(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void goToIssueOrdersPhase() {
        try {
            resumeOrThrow();
            d_gameEngine.getGameState().assignReinforcements();
            d_gameEngine.setGamePhase(new IssueOrdersPhase(d_gameEngine));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            goToFinishPhase();
        }
    }
}
