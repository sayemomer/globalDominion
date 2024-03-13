package controllers;

import models.GameState;
import models.orders.*;
import models.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    public static ArrayList<Order> takeOrderCommands(Player p_ownerPlayer) {
        ArrayList<Order> l_orders = new ArrayList<>();
        while (l_orders.isEmpty()) {
            System.out.print("issue-order-" + p_ownerPlayer.getName() + ">");
            String[] inputString = d_scanner.nextLine().toLowerCase().split("\\s+");
            String command = inputString[0];
            String[] args = Arrays.copyOfRange(inputString, 1, inputString.length);
            if (command.equals("exit")) {
                System.out.println("Exiting the game...");
                System.exit(0);
            } else if (command.equals(Command.DEPLOY)) {
                l_orders.addAll(handleDeployOrderCommand(args, p_ownerPlayer));
                break;
            } else if (command.equals(Command.ADVANCE)) {
                l_orders.add(handleAdvanceOrderCommand(args, p_ownerPlayer));
                break;
            } else if (command.equals(Command.BOMB)) {
                l_orders.add(handleBombOrderCommand(args, p_ownerPlayer));
                break;
            } else if (command.equals(Command.BLOCKADE)) {
                l_orders.add(handleBlockadeOrderCommand(args, p_ownerPlayer));
                break;
            } else if (command.equals(Command.AIRLIFT)) {
                l_orders.add(handleAirliftOrderCommand(args, p_ownerPlayer));
                break;
            } else if (command.equals(Command.NEGOTIATE)) {
                l_orders.add(handleNegotiateOrderCommand(args, p_ownerPlayer));
                break;
            } else if (command.equals(Command.SHOW_MAP)) {
                d_gameState.printMap();
            } else {
                System.out.println("Invalid command. Please try again.");
            }
        }

        return l_orders;
    }

    /**
     * handle deploy order command
     *
     * @param p_ownerPlayer can be any instance of player
     * @param args          the command arguments
     */
    public static ArrayList<DeployOrder> handleDeployOrderCommand(String[] args, Player p_ownerPlayer) {
        ArrayList<DeployOrder> orders = new ArrayList<>();
        try {

            if (args.length % 2 != 0) {
                throw new Exception("Invalid number of arguments. Correct Syntax: \n\t" + Command.DEPLOY_SYNTAX);
            }

            for (int i = 0; i < args.length; i += 2) {
                DeployOrder order = null;
                try {
                    int l_countryId = Integer.parseInt(args[i]);
                    int l_numReinforcements = Integer.parseInt(args[i + 1]);
                    order = new DeployOrder(d_gameState, p_ownerPlayer, l_countryId, l_numReinforcements);
                    orders.add(order);

                } catch (NumberFormatException e) {
                    System.out.println("Error with DeployOrder creation at index " + i / 2 + " : " + "Invalid country ID " + args[i] + " or number of reinforcements " + args[i + 1]);
                } catch (Exception e) {
                    System.out.println("Error with DeployOrder creation at index " + i / 2 + " : " + e.getMessage());
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return orders;
    }

    public static AdvanceOrder handleAdvanceOrderCommand(String[] args, Player p_ownerPlayer) {
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

    /**
     * handle bomb order command
     *
     * @param p_ownerPlayer can be any instance of player
     * @param strings       the command arguments
     * @return the bomb order
     */


    public static Bomb handleBombOrderCommand(String[] strings, Player p_ownerPlayer) {
        Bomb l_order = null;
        try {
            if (strings.length != 1) {
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.BOMB_SYNTAX);
            }
            int l_countryId = Integer.parseInt(strings[0]);
            l_order = new Bomb(d_gameState, p_ownerPlayer, l_countryId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid country ID.");
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return l_order;
    }

    public static Order handleBlockadeOrderCommand(String[] strings, Player p_ownerPlayer) {
        Blockade l_order = null;
        try {
            if (strings.length != 1) {
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.BLOCKADE_SYNTAX);
            }
            int l_countryId = Integer.parseInt(strings[0]);
            l_order = new Blockade(d_gameState, p_ownerPlayer, l_countryId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid country ID.");
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return l_order;
    }

    public static Order handleAirliftOrderCommand(String[] strings, Player p_ownerPlayer) {
        Airlift l_order = null;
        try {
            if (strings.length != 3) {
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.AIRLIFT_SYNTAX);
            }
            int l_countryFromId = Integer.parseInt(strings[0]);
            int l_countryToId = Integer.parseInt(strings[1]);
            int l_numReinforcements = Integer.parseInt(strings[2]);
            l_order = new Airlift(d_gameState, p_ownerPlayer, l_countryFromId, l_countryToId, l_numReinforcements);
        } catch (NumberFormatException e) {
            System.out.println("Invalid country ID or number of reinforcements.");
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return l_order;
    }

    public static Order handleNegotiateOrderCommand(String[] strings, Player p_ownerPlayer) {
        Deplomacy l_order = null;
        try {
            if (strings.length != 1) {
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.NEGOTIATE_SYNTAX);
            }
            String l_playerName = strings[0];
            l_order = new Deplomacy(d_gameState, p_ownerPlayer, l_playerName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return l_order;
    }
}
