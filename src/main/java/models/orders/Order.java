package models.orders;

import models.GameState;
import models.Player;

/**
 * The Order class is responsible for keeping track of the order's state.
 */
public abstract class Order {

    /**
     * The game state
     */

    protected final GameState d_gameState;

    /**
     * The owner of the order
     */
    protected final Player d_owner;


    /**
     * Constructor for the Order class
     * @param p_gameState the game state
     * @param p_owner the owner of the order
     */

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
