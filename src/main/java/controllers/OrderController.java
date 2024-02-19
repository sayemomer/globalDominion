package controllers;

import models.GameState;
import models.orders.DeployOrder;
import models.orders.Order;
import models.Player;

import java.util.Arrays;
import java.util.Scanner;

/**
 * The OrderController class is responsible for handling the order commands.
 */
public class OrderController {
    private final GameState d_gameState;
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
        int l_countryId;
        int l_numReinforcements;
        try {
            if (args.length != 2) {
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.DEPLOY_SYNTAX);
            }

            l_countryId = Integer.parseInt(args[0]);
            l_numReinforcements = Integer.parseInt(args[1]);

            if (!p_ownerPlayer.getCountryIds().contains(l_countryId)) {
                throw new Exception("The given country is not owned by the player. Please try again.");
            }
            if (l_numReinforcements < 0) {
                throw new Exception("Invalid number of reinforcement. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid country ID or number of reinforcements.");
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return new DeployOrder(p_ownerPlayer, l_countryId, l_numReinforcements);
    }

    /**
     * Validates the order
     *
     * @param p_order can be any instance of Order
     */
    public static boolean validateOrder(Order p_order) {
        try {
            if (p_order instanceof DeployOrder) {
                validateDeployOrder((DeployOrder) p_order);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Validates the deploy order
     *
     * @param p_order must be an instance of DeployOrder
     * @throws Exception if the order is not in players territories or if the player does not have enough reinforcements
     */
    public static void validateDeployOrder(DeployOrder p_order) throws Exception {
        Player l_player = p_order.getOwnerPlayer();
        if (p_order.getNumReinforcements() > l_player.getReinforcementPoll())
            throw new Exception("The player does not have enough reinforcements. Please try again.");
        if (!l_player.getCountryIds().contains(p_order.getCountryId()))
            throw new Exception("The given country is not owned by the player. Please try again.");
    }

}
