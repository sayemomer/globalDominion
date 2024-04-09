package models.behaviors;

import controllers.OrderController;
import models.Country;
import models.GameState;
import models.Player;
import models.orders.Order;

import java.util.Map;

/**
 * Human strategy behavior class
 */
public class HumanStrategyBehavior extends StrategyBehavior {
    public HumanStrategyBehavior(Player p_player, GameState p_gameState) {
        super(p_player, p_gameState);
    }

    @Override
    public Order issueOtherOrders() {
        return issueOrder();
    }

    @Override
    public Order issueDeployOrder() {
        return issueOrder();
    }

    @Override
    public Order issueOrder() {
        while (true) {
            if (d_player.getCountries().isEmpty())
                return null;
            Order l_order = OrderController.takeOrderCommands(d_player);
            if (l_order != null) return l_order;
        }
    }
}
