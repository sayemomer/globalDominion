package controllers;

import models.GameState;
import models.orders.AdvanceOrder;
import models.orders.DeployOrder;
import models.orders.Order;
import models.Player;

import java.util.Arrays;
import java.util.Scanner;

/**
 * The OrderController class is responsible for handling the order commands.
 * MUST BE INITIALIZED BEFORE ANY USE
 */
public class OrderController {
    private static GameState d_gameState;
    private static Scanner d_scanner;

    public OrderController(GameState p_gameState, Scanner p_scanner) {
        d_gameState = p_gameState;
        d_scanner = p_scanner;
    }

    /**
     * take order commands
     *
     * @param p_ownerPlayer can be any instance of player
     */
    public static Order takeOrderCommands(Player p_ownerPlayer) {
        System.out.print("issue-order-" + p_ownerPlayer.getName() + ">");
        Order l_order = null;
        while (l_order == null) {
            String[] inputString = d_scanner.nextLine().toLowerCase().split("\\s+");
            String command = inputString[0];
            String[] args = Arrays.copyOfRange(inputString, 1, inputString.length);
            if (command.equals("exit")) {
                System.out.println("Exiting the game...");
                System.exit(0);
            } else if (command.equals(Command.DEPLOY)) {
                l_order = handleDeployOrderCommand(args, p_ownerPlayer);
                break;
            } else if (command.equals(Command.ADVANCE)) {
                l_order = handleAdvanceOrderCommand(args, p_ownerPlayer);
                break;
            } else {
                System.out.println("Invalid command. Please try again.");
            }
        }

        return l_order;
    }

    /**
     * handle deploy order command
     *
     * @param p_ownerPlayer can be any instance of player
     * @param args          the command arguments
     */
    public static DeployOrder handleDeployOrderCommand(String[] args, Player p_ownerPlayer) {
        DeployOrder order = null;
        try {

            if (args.length != 2)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.DEPLOY_SYNTAX);

            int l_countryId = Integer.parseInt(args[0]);
            int l_numReinforcements = Integer.parseInt(args[1]);

            order = new DeployOrder(d_gameState, p_ownerPlayer, l_countryId, l_numReinforcements);

        } catch (NumberFormatException e) {
            System.out.println("Invalid country ID or number of reinforcements.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return order;
    }

    public static AdvanceOrder handleAdvanceOrderCommand(String[] args, Player p_ownerPlayer){
        AdvanceOrder order = null;

        try {
            if (args.length != 3) {
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.ADVANCE_SYNTAX);
            }


            int l_countryFromId = Integer.parseInt(args[0]);
            int l_countryToId = Integer.parseInt(args[1]);
            int l_numReinforcements = Integer.parseInt(args[2]);

            order = new AdvanceOrder(d_gameState, p_ownerPlayer, l_countryFromId, l_countryToId, l_numReinforcements);

        } catch (NumberFormatException e) {
            System.out.println("Invalid country ID or number of reinforcements.");
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return order;
    }


}
