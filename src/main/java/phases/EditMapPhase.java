package phases;

import controllers.Command;
import services.CustomPrint;

import java.util.Arrays;

/**
 * The EditMapPhase class is responsible for handling the map edit phase of the game.
 */

public class EditMapPhase extends Phase {

    /**
     * Constructor for the EditMapPhase class.
     * @param p_gameEngine The game engine.
     */
    public EditMapPhase(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void printAvailableCommands() {
        CustomPrint.println("*-*-* MAP EDITOR *-*-*");
        CustomPrint.println("Available commands: ");
        CustomPrint.println("  " + Command.EDIT_CONTINENT_SYNTAX);
        CustomPrint.println("  " + Command.EDIT_COUNTRY_SYNTAX);
        CustomPrint.println("  " + Command.EDIT_NEIGHBOR_SYNTAX);
        CustomPrint.println("  " + Command.VALIDATE_MAP_SYNTAX);
        CustomPrint.println("  " + Command.SAVE_MAP_SYNTAX);
        CustomPrint.println("  " + Command.SHOW_MAP_SYNTAX);
        CustomPrint.println("Type 'exit' to exit the game.");
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
            CustomPrint.print("map-editor> ");
            String[] l_inputString = d_gameEngine.getScanner().nextLine().trim().toLowerCase().split("\\s+");
            String l_command = l_inputString[0];
            String[] l_args = Arrays.copyOfRange(l_inputString, 1, l_inputString.length);

            switch (l_command) {
                case "proceed":
                    CustomPrint.println("Proceeding to the next phase...");
                    goToStartUpPhase();
                    break label;
                case "exit":
                    CustomPrint.println("Exiting the game...");
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
                    // Ask the user about the map format and save the map
                    System.out.println("Enter the map format (domination 1 /conquest 2): ");
                    switch (d_gameEngine.getScanner().nextLine()) {
                        case "1":
                            d_gameEngine.getGameState().setMapFormat("domination");
                            break;
                        case "2":
                            d_gameEngine.getGameState().setMapFormat("conquest");
                            break;
                        default:
                            CustomPrint.println("Invalid map format. Please try again.");
                            break;
                    }
                    if (d_gameEngine.getMapController().handleSaveMapCommand(l_args)) {
                        goToStartUpPhase();
                        return;
                    }
                    break;
                case Command.SHOW_MAP:
                    d_gameEngine.getGameState().printMap();
                    break;
                default:
                    CustomPrint.println("Invalid input. Please try again.");
                    break;
            }
        }

    }

    /**
     * This method is responsible for changing the game phase to the start up phase.
     */

    public void goToStartUpPhase() {
        d_gameEngine.setGamePhase(new StartupPhase(d_gameEngine));
    }


}
