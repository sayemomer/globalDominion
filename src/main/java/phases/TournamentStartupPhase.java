package phases;

import controllers.Command;
import controllers.TournamentController;
import models.Tournament;
import services.CustomPrint;

import java.util.Arrays;

/**
 * Tournament startup phase

 */
public class TournamentStartupPhase extends Phase {
    /**
     * Constructor for the Phase class
     *
     * @param p_gameEngine game engine
     */
    public TournamentStartupPhase(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void printAvailableCommands() {
        CustomPrint.println("*-*-* STARTUP PHASE *-*-*");
        CustomPrint.println("Available commands: ");
        CustomPrint.println("  " + Command.TOURNAMENT_SYNTAX);
    }

    @Override
    public boolean run() {
        printAvailableCommands();
        TournamentController l_tournamentController = new TournamentController();
        while (true) {
            String[] l_input = d_gameEngine.getScanner().nextLine().trim().split("\\s+");
            if (l_input[0].equals(Command.TOURNAMENT)) {
                Tournament l_tournament = l_tournamentController.handleTournamentCommand(Arrays.copyOfRange(l_input, 1, l_input.length));
                if (l_tournament != null) {
                    l_tournament.startTournament();
                    break;
                }
            } else {
                CustomPrint.println("Invalid command. Please try again.");
            }
        }
        return false;
    }
}
