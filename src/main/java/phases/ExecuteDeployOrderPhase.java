package phases;

/**
 * Execute deploy order phase e
 */

public class ExecuteDeployOrderPhase extends ExecuteOrdersPhase {

    /**
     * cunstructor of ExecuteDeployOrderPhase
     * @param p_gameEngine
     */
    public ExecuteDeployOrderPhase(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * going to issue order phase
     */
    @Override
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
