public class GameEngine {
    void startGameLoop() {
        System.out.println("Game loop is running...");
        assignReinforcementsPhase();
        issueOrdersPhase();
        executeOrdersPhase();
    }

    void assignReinforcementsPhase() {
        System.out.println("Assigning reinforcements...");
    }

    void issueOrdersPhase() {
        System.out.println("Issuing orders...");
    }

    void executeOrdersPhase() {
        System.out.println("Executing orders...");
    }

}
