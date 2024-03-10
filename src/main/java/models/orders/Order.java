package models.orders;

import models.GameState;
import models.Player;

public class Order {
    private int d_orderId;

    protected GameState d_gameState;



    public Order(GameState p_gameState) {
        this.d_gameState = p_gameState;
    }

    public Order() {

    }

    /**
     * This method must be overridden by the subclasses
     */
    public void execute() {

    }

    public int getOrderID() {
        return d_orderId;
    }
    
    public GameState getGameState() {
        return d_gameState;
    }

}
