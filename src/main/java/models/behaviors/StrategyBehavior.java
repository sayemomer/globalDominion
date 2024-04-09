package models.behaviors;

import controllers.OrderController;
import models.GameState;
import models.Player;
import models.orders.Order;
import phases.IssueDeployOrder;

import java.io.Serializable;
import java.util.Map;
/**
 * An abstract class representing the behavior of a player's strategy in the game.
 */
public abstract class StrategyBehavior implements Serializable {
    /**
     * player that is using the behavior
     */
    Player d_player;
    /**
     * game state
     */
    GameState d_gameState;

    /**
     * converts string to behavior
     * used to parse user input
     */
    public static Map<String, Class<? extends StrategyBehavior>> STR_TO_BEHAVIOR = Map.of(
            "human", HumanStrategyBehavior.class,
            "aggressive", AggressiveStrategyBehavior.class,
            "benevolent", BenevolentStrategyBehavior.class,
            "random", RandomStrategyBehavior.class,
            "cheater", CheaterStrategyBehavior.class
    );

    /**
     * Constructor for StrategyBehavior
     * @param p_player player initialization
     * @param p_gameState game state initialization
     */
    public StrategyBehavior(Player p_player, GameState p_gameState) {
        d_player = p_player;
        d_gameState = p_gameState;
    }

    /**
     * This method is not to be overridden.
     * It is responsible for issuing orders based on the implemented strategy.
     * @return An Order object representing the issued order.
     */
    public Order issueOrder() {
        if (d_player.getCountryIds().isEmpty())
            return null;
        if (OrderController.d_gameEngine.getGamePhase() instanceof IssueDeployOrder) {
            return issueDeployOrder();
        } else {
            return issueOtherOrders();
        }
    }

    /**
     * Issues orders as part of the strategy, excluding deploy orders.
     *
     * This method is responsible for determining and issuing orders other than deploy orders,
     * based on the implemented strategy.
     *
     * @return An Order object representing the issued order.
     */
    protected abstract Order issueOtherOrders();


    /**
     * Issues a deploy order as part of the strategy.
     *
     * This method is responsible for determining and issuing a deploy order based on the implemented strategy.
     *
     * @return An Order object representing the issued deploy order.
     */
    protected abstract Order issueDeployOrder();

}
