package phases;

import controllers.Command;
import models.GameState;
import services.CustomPrint;

import java.util.Arrays;

/**
 * This class is responsible for the startup phase of the game.
 */

public class StartupPhase extends Phase {

    /**
     * Constructor for the StartupPhase class
     * @param p_gameEngine game engine
     */
    public StartupPhase(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * Print available commands
     */
    @Override
    public void printAvailableCommands() {
        CustomPrint.println("*-*-* STARTUP PHASE *-*-*");
        CustomPrint.println("Available commands: ");
        CustomPrint.println("  " + Command.GAME_PLAYER_SYNTAX);
        CustomPrint.println("  " + Command.LOAD_MAP_SYNTAX);
        CustomPrint.println("  " + Command.EDIT_MAP_SYNTAX);
        CustomPrint.println("  " + Command.ASSIGN_COUNTRIES_SYNTAX);
        CustomPrint.println("Type 'proceed' to proceed to the next phase.");
        CustomPrint.println("Type 'exit' to exit the game.");
    }

    /**
     * This method is responsible for the startup phase of the game.
     * gets user input and calls the appropriate method in the controllers.
     */
    @Override
    public boolean run() {
        printAvailableCommands();
        label:
        while (true) {
            CustomPrint.print("startup-phase> ");
            String[] l_inputString = d_gameEngine.getScanner().nextLine().trim().toLowerCase().split("\\s+");
            String l_command = l_inputString[0];
            String[] l_args = Arrays.copyOfRange(l_inputString, 1, l_inputString.length);

            switch (l_command) {
                case "proceed":
                    if (goToIssueOrdersPhase())
                        break label;
                    break;
                case "exit":
                    CustomPrint.println("Exiting the game...");
                    System.exit(0);
                case Command.SHOW_MAP:
                    d_gameEngine.getGameState().printMap();
                    CustomPrint.println("  " + Command.EDIT_MAP_SYNTAX);
                    CustomPrint.println("  " + Command.ASSIGN_COUNTRIES_SYNTAX);
                    break;
                case Command.GAME_PLAYER:
                    d_gameEngine.getPlayerController().handleGamePlayerCommand(l_args);
                    break;
                case Command.LOAD_MAP:
                    if (d_gameEngine.getMapController().handleloadMapCommand(l_args)) {
                        goToEditMapPhase();
                        break label;
                    }
                    break;
                case Command.EDIT_MAP:
                    if (d_gameEngine.getMapController().handleEditMapCommand(l_args)) {
                        goToEditMapPhase();
                        break label;
                    }
                    break;
                case Command.ASSIGN_COUNTRIES:
                    d_gameEngine.getCountryController().handleAssignCountriesCommand(l_args);
                    break;
                default:
                    System.err.println("Invalid input. Please try again.");
                    break;
            }
        }
        return true;
    }

    private void playersAddedOrThrow() throws Exception {
        if (d_gameEngine.getGameState().getPlayers().isEmpty())
            throw new Exception("At least a players is required to proceed.");
    }

    private void mapLoadedOrThrow() throws Exception {
        if (!d_gameEngine.getGameState().isActionDone(GameState.GameAction.VALID_MAP_LOADED))
            throw new Exception("A valid map is not loaded.");
    }

    private void countriesAssignedOrThrow() throws Exception {
        if (!d_gameEngine.getGameState().isActionDone(GameState.GameAction.COUNTRIES_ASSIGNED))
            throw new Exception("Countries are not assigned.");
    }

    /**
     * This method is responsible for the transition from the startup phase to the edit map phase.
     * It changes the game phase to the edit map phase.
     */

    public void goToEditMapPhase() {
        d_gameEngine.setGamePhase(new EditMapPhase(d_gameEngine));
    }

    /**
     * This method is responsible for the transition from the startup phase to the issue orders phase.
     * It checks if the map is loaded, players are added and countries are assigned.
     * If all the conditions are met, it changes the game phase to the issue orders phase.
     *
     * @return true if the phase change is successful, false otherwise
     */
    public boolean goToIssueOrdersPhase() {
        try {
            mapLoadedOrThrow();
            playersAddedOrThrow();
            countriesAssignedOrThrow();
            d_gameEngine.getGameState().assignReinforcements();
            d_gameEngine.setGamePhase(new IssueDeployOrder(d_gameEngine));
            return true;
        } catch (Exception e) {
            System.err.println("Phase change error: " + e.getMessage());
            return false;
        }
    }
}
