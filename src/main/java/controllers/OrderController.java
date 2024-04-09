package controllers;

import models.GameContext;
import models.GameState;
import models.orders.*;
import models.Player;
import phases.GameEngine;
import phases.IssueDeployOrder;
import services.CustomPrint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * The OrderController class is responsible for handling the order commands.
 * MUST BE INITIALIZED BEFORE ANY USE
 */
public class OrderController implements Serializable {
    private static GameState d_gameState;
    private static Scanner d_scanner;
    /**
     * The game engine instance used to manage the game state and control the game flow.
     */
    public static GameEngine d_gameEngine;

    /**
     * Reset the states of the OrderController
     * @param p_gameState  The new game state to set in the OrderController.
     * @param p_gameEngine The new game engine to set in the OrderController
     */
    public static void resetStates(GameState p_gameState, GameEngine p_gameEngine) {
        OrderController.d_gameState = p_gameState;
        OrderController.d_gameEngine = p_gameEngine;
    }

    /**
     * Constructor for the OrderController class.
     *
     * @param p_gameState The game state.
     * @param p_scanner   The scanner object.
     */
    public OrderController(GameState p_gameState, Scanner p_scanner) {
        d_gameState = p_gameState;
        d_scanner = p_scanner;
    }

    
    /**
     * Constructor for the OrderController class.
     *
     * @param p_gameEngine The game engine.
     */

    public OrderController(GameEngine p_gameEngine) {
        d_gameEngine = p_gameEngine;
        d_gameState = p_gameEngine.getGameState();
        d_scanner = p_gameEngine.getScanner();
    }

    /**
     * take order commands
     *
     * @param p_ownerPlayer can be any instance of player
     *
     * @return the order
     */
    public static Order takeOrderCommands(Player p_ownerPlayer) {
        if (d_gameEngine != null && d_gameEngine.getGamePhase() instanceof IssueDeployOrder) {
            Order l_order = null;
            while (l_order == null) {
                CustomPrint.print("issue-order-" + p_ownerPlayer.getName() + ">");
                String[] inputString = d_scanner.nextLine().toLowerCase().split("\\s+");
                String command = inputString[0];
                String[] args = Arrays.copyOfRange(inputString, 1, inputString.length);
                if (command.equals("exit")) {
                    CustomPrint.println("Exiting the game...");
                    System.exit(0);
                } else if (command.equals(Command.DEPLOY)) {
                    l_order = handleDeployOrderCommand(args, p_ownerPlayer);
                    break;
                } else if (command.equals(Command.SHOW_MAP)) {
                    d_gameState.printMap();
                } else if (command.equals(Command.SAVE_GAME)) {
                    ContextController.handleSaveGame(args, d_gameState, d_gameEngine);
                } else if (command.equals(Command.LOAD_GAME)) {
                    ContextController.handleLoadGame(args);
                }
                else {
                    CustomPrint.println("Invalid command. Please try again. \nplayers can only deploy in this phase.");
                }
            }

            return l_order;
        }

        Order l_order = null;
        while (l_order == null) {
            CustomPrint.print("issue-order-" + p_ownerPlayer.getName() + ">");
            String[] inputString = d_scanner.nextLine().toLowerCase().split("\\s+");
            String command = inputString[0];
            String[] args = Arrays.copyOfRange(inputString, 1, inputString.length);
            if (command.equals("exit")) {
                CustomPrint.println("Exiting the game...");
                System.exit(0);
            }  else if (command.equals(Command.ADVANCE)) {
                l_order = handleAdvanceOrderCommand(args, p_ownerPlayer);
                break;
            } else if (command.equals(Command.BOMB)) {
                l_order = handleBombOrderCommand(args, p_ownerPlayer);
                break;
            } else if (command.equals(Command.BLOCKADE)) {
                l_order = handleBlockadeOrderCommand(args, p_ownerPlayer);
                break;
            } else if (command.equals(Command.AIRLIFT)) {
                l_order = handleAirliftOrderCommand(args, p_ownerPlayer);
                break;
            } else if (command.equals(Command.NEGOTIATE)) {
                l_order = handleNegotiateOrderCommand(args, p_ownerPlayer);
                break;
            } else if (command.equals(Command.SHOW_MAP)) {
                d_gameState.printMap();
            } else if (command.equals(Command.SAVE_GAME)) {
                ContextController.handleSaveGame(args, d_gameState, d_gameEngine);
            } else if (command.equals(Command.LOAD_GAME)) {
                ContextController.handleLoadGame(args);
            } else {
                CustomPrint.println("Invalid command. Please try again.");
            }
        }

        return l_order;
    }

    /**
     * handle deploy order command
     *
     * @param p_ownerPlayer can be any instance of player
     * @param args          the command arguments
     * @return the deploy order
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
            CustomPrint.println("Invalid country ID or number of reinforcements.");
        } catch (Exception e) {
            CustomPrint.println(e.getMessage());
        }
        return order;
    }

    /**
     * handle advance order command
     *
     * @param p_ownerPlayer can be any instance of player
     * @param args          the command arguments
     * @return the advance order
     */

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
            CustomPrint.println("Invalid country ID or number of reinforcements.");
            return null;
        } catch (Exception e) {
            CustomPrint.println(e.getMessage());
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
    public static BombOrder handleBombOrderCommand(String[] strings, Player p_ownerPlayer) {
        BombOrder l_order = null;
        try {
            if (strings.length != 1) {
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.BOMB_SYNTAX);
            }
            int l_countryId = Integer.parseInt(strings[0]);
            l_order = new BombOrder(d_gameState, p_ownerPlayer, l_countryId);
        } catch (NumberFormatException e) {
            CustomPrint.println("Invalid country ID.");
            return null;
        } catch (Exception e) {
            CustomPrint.println(e.getMessage());
            return null;
        }
        return l_order;
    }

    /**
     * handle blockade order command
     *
     * @param p_ownerPlayer can be any instance of player
     * @param strings       the command arguments
     * @return the blockade order
     */

    public static Order handleBlockadeOrderCommand(String[] strings, Player p_ownerPlayer) {
        BlockadeOrder l_order = null;
        try {
            if (strings.length != 1) {
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.BLOCKADE_SYNTAX);
            }
            int l_countryId = Integer.parseInt(strings[0]);
            l_order = new BlockadeOrder(d_gameState, p_ownerPlayer, l_countryId);
        } catch (NumberFormatException e) {
            CustomPrint.println("Invalid country ID.");
            return null;
        } catch (Exception e) {
            CustomPrint.println(e.getMessage());
            return null;
        }
        return l_order;
    }

    /**
     * handle airlift order command
     *
     * @param p_ownerPlayer can be any instance of player
     * @param strings       the command arguments
     * @return the airlift order
     */

    public static Order handleAirliftOrderCommand(String[] strings, Player p_ownerPlayer) {
        AirliftOrder l_order = null;
        try {
            if (strings.length != 3) {
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.AIRLIFT_SYNTAX);
            }
            int l_countryFromId = Integer.parseInt(strings[0]);
            int l_countryToId = Integer.parseInt(strings[1]);
            int l_numReinforcements = Integer.parseInt(strings[2]);
            l_order = new AirliftOrder(d_gameState, p_ownerPlayer, l_countryFromId, l_countryToId, l_numReinforcements);
        } catch (NumberFormatException e) {
            CustomPrint.println("Invalid country ID or number of reinforcements.");
            return null;
        } catch (Exception e) {
            CustomPrint.println(e.getMessage());
            return null;
        }
        return l_order;
    }

    /**
     * handle negotiate order command
     *
     * @param p_ownerPlayer can be any instance of player
     * @param strings       the command arguments
     * @return to negotiate order
     */

    public static Order handleNegotiateOrderCommand(String[] strings, Player p_ownerPlayer) {
        DiplomacyOrder l_order = null;

        try {
            if (strings.length != 1) {
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.NEGOTIATE_SYNTAX);
            }
            String l_playerName = strings[0];
            l_order = new DiplomacyOrder(d_gameState, p_ownerPlayer, l_playerName);
        } catch (Exception e) {
            CustomPrint.println(e.getMessage());
            return null;
        }

        return l_order;
    }
}
