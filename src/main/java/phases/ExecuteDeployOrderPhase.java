package phases;

import config.AppConfig;
import services.CustomPrint;

/**
 * Execute deploy order phase e
 */

public class ExecuteDeployOrderPhase extends ExecuteOrdersPhase {

    /**
     * cunstructor of ExecuteDeployOrderPhase
     * @param p_gameEngine game engine
     */
    public ExecuteDeployOrderPhase(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void printAvailableCommands() {
        CustomPrint.println("*-*-* EXECUTE ORDERS PHASE *-*-*");
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
            CustomPrint.println(e.getMessage());
            goToFinishPhase();
        }
    }
}
