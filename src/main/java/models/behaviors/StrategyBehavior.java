package models.behaviors;

import controllers.OrderController;
import models.Country;
import models.GameState;
import models.Player;
import models.orders.Order;
import phases.IssueDeployOrder;

import java.util.Map;

public abstract class StrategyBehavior {
    Player d_player;
    GameState d_gameState;

    public StrategyBehavior(Player p_player, GameState p_gameState) {
        d_player = p_player;
        d_gameState = p_gameState;
    }

    public Order issueOrder() {
        if (OrderController.d_gameEngine.getGamePhase() instanceof IssueDeployOrder) {
            return issueDeployOrder();
        } else {
            return issueOtherOrders();
        }
    }

    protected abstract Order issueOtherOrders();

    protected abstract Order issueDeployOrder();

}
