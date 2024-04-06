package phases;

import controllers.Command;
import controllers.TournamentController;
import models.Tournament;

import java.util.Arrays;

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
        System.out.println("*-*-* STARTUP PHASE *-*-*");
        System.out.println("Available commands: ");
        System.out.println("  " + Command.TOURNAMENT_SYNTAX);
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
                System.out.println("Invalid command. Please try again.");
            }
        }
        return false;
    }
}
