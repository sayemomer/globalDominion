package controllers;

import models.GameState;
import models.orders.DeployOrder;
import models.orders.Order;
import models.Player;

import java.util.Arrays;
import java.util.Scanner;

public class OrderController {
    private final GameState gameState;
    private static Scanner scanner;

    public OrderController(GameState p_gameState, Scanner p_scanner) {
        gameState = p_gameState;
        scanner = p_scanner;
    }


    public static Order takeOrderCommands(Player p_ownerPlayer) {
        System.out.print("issue-order-" + p_ownerPlayer.getName() + ">");
        Order order = null;
        while (order == null) {
            String[] inputString = scanner.nextLine().toLowerCase().split("\\s+");
            String command = inputString[0];
            String[] args = Arrays.copyOfRange(inputString, 1, inputString.length);
            if (command.equals("exit")) {
                System.out.println("Exiting the game...");
                System.exit(0);
            } else if (command.equals(Command.DEPLOY)) {
                order = handleDeployOrderCommand(args, p_ownerPlayer);
                break;
            } else {
                System.out.println("Invalid command. Please try again.");
            }
        }

        return order;
    }

    public static DeployOrder handleDeployOrderCommand(String[] args, Player p_ownerPlayer) {
        int countryId;
        int numReinforcements;
        try {
            if (args.length != 2) {
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.DEPLOY_SYNTAX);
            }

            countryId = Integer.parseInt(args[0]);
            numReinforcements = Integer.parseInt(args[1]);

            if (!p_ownerPlayer.getCountryIds().contains(countryId)) {
                throw new Exception("The given country is not owned by the player. Please try again.");
            }
            if (numReinforcements < 0) {
                throw new Exception("Invalid number of reinforcement. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid country ID or number of reinforcements.");
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return new DeployOrder(p_ownerPlayer, countryId, numReinforcements);
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
