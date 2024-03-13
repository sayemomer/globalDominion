package phases;

import controllers.Command;

import java.util.Arrays;

public class EditMapPhase extends Phase {
    public EditMapPhase(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void printAvailableCommands() {
        System.out.println("*-*-* MAP EDITOR *-*-*");
        System.out.println("Available commands: ");
        System.out.println("  " + Command.EDIT_CONTINENT_SYNTAX);
        System.out.println("  " + Command.EDIT_COUNTRY_SYNTAX);
        System.out.println("  " + Command.EDIT_NEIGHBOR_SYNTAX);
        System.out.println("  " + Command.VALIDATE_MAP_SYNTAX);
        System.out.println("  " + Command.SAVE_MAP_SYNTAX);
        System.out.println("  " + Command.SHOW_MAP_SYNTAX);
        System.out.println("Type 'exit' to exit the game.");
    }

    
    /**
     * This method is responsible for the map edit phase of the game.
     * gets user input and calls the appropriate method in the controllers.
     */
    @Override
    public void run() {
        printAvailableCommands();
        label:
        while (true) {
            System.out.print("map-editor> ");
            String[] l_inputString = d_gameEngine.getScanner().nextLine().trim().toLowerCase().split("\\s+");
            String l_command = l_inputString[0];
            String[] l_args = Arrays.copyOfRange(l_inputString, 1, l_inputString.length);

            switch (l_command) {
                case "proceed":
                    System.out.println("Proceeding to the next phase...");
                    goToStartUpPhase();
                    break label;
                case "exit":
                    System.out.println("Exiting the game...");
                    System.exit(0);
                    return;
                case Command.EDIT_CONTINENT:
                    d_gameEngine.getCountryController().handleEditContinentCommand(l_args);
                    break;
                case Command.EDIT_COUNTRY:
                    d_gameEngine.getCountryController().handleEditCountryCommand(l_args);
                    break;
                case Command.EDIT_NEIGHBOR:
                    d_gameEngine.getCountryController().handleEditNeighborCommand(l_args);
                    break;
                case Command.VALIDATE_MAP:
                    d_gameEngine.getMapController().handleValidateMapCommand(l_args);
                    break;
                case Command.SAVE_MAP:
                    if (d_gameEngine.getMapController().handleSaveMapCommand(l_args)) {
                        goToStartUpPhase();
                        return;
                    }
                    break;
                case Command.SHOW_MAP:
                    d_gameEngine.getGameState().printMap();
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        }

    }

    public void goToStartUpPhase() {
        d_gameEngine.setGamePhase(new StartupPhase(d_gameEngine));
    }


}
