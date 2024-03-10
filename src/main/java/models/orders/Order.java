package models.orders;

import models.GameState;
import models.Player;

public abstract class Order {

    protected final GameState d_gameState;
    protected final Player d_owner;

    public Order(GameState p_gameState, Player p_owner) {
        assert p_gameState != null;
        assert p_owner != null;
        d_gameState = p_gameState;
        d_owner = p_owner;
    }

    /**
     * avoid creating Order without gameState or owner
     */
    private Order() {
        d_gameState = null;
        d_owner = null;
    }

    /**
     * This method must be overridden by the subclasses.
     * executes the order
     */
    public abstract void execute();

    /**
     * This method must be overridden by the subclasses.
     * validates the order
     */
    protected abstract void validate();

}
