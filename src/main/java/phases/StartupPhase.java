package phases;

import controllers.Command;
import models.GameState;

import java.util.Arrays;

public class StartupPhase extends Phase {
    public StartupPhase(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * Print available commands
     */
    @Override
    public void printAvailableCommands() {
        System.out.println("*-*-* STARTUP PHASE *-*-*");
        System.out.println("Available commands: ");
        System.out.println("  " + Command.GAME_PLAYER_SYNTAX);
        System.out.println("  " + Command.LOAD_MAP_SYNTAX);
        System.out.println("  " + Command.EDIT_MAP_SYNTAX);
        System.out.println("  " + Command.ASSIGN_COUNTRIES_SYNTAX);
        System.out.println("Type 'proceed' to proceed to the next phase.");
        System.out.println("Type 'exit' to exit the game.");
    }

    /**
     * This method is responsible for the startup phase of the game.
     * gets user input and calls the appropriate method in the controllers.
     */
    @Override
    public void run() {
        printAvailableCommands();
        label:
        while (true) {
            System.out.print("startup-phase> ");
            String[] l_inputString = d_gameEngine.getScanner().nextLine().trim().toLowerCase().split("\\s+");
            String l_command = l_inputString[0];
            String[] l_args = Arrays.copyOfRange(l_inputString, 1, l_inputString.length);

            switch (l_command) {
                case "proceed":
                    if (goToIssueOrdersPhase())
                        break label;
                    break;
                case "exit":
                    System.out.println("Exiting the game...");
                    System.exit(0);
                case Command.SHOW_MAP:
                    d_gameEngine.getGameState().printMap();
                    System.out.println("  " + Command.EDIT_MAP_SYNTAX);
                    System.out.println("  " + Command.ASSIGN_COUNTRIES_SYNTAX);
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


    public void goToEditMapPhase() {
        d_gameEngine.setGamePhase(new EditMapPhase(d_gameEngine));
    }

    public boolean goToIssueOrdersPhase() {
        try {
            mapLoadedOrThrow();
            playersAddedOrThrow();
            d_gameEngine.getGameState().assignReinforcements();
            countriesAssignedOrThrow();
            d_gameEngine.setGamePhase(new IssueDeployOrder(d_gameEngine));
            return true;
        } catch (Exception e) {
            System.err.println("Phase change error: " + e.getMessage());
            return false;
        }
    }
}
