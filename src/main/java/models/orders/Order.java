package models.orders;

import models.Player;

public class Order {
    private int d_orderId;
    protected Player d_owner;

    public Order(Player p_owner) {
        this.d_owner = p_owner;
    }

    public Order() {

    }

    public void execute() {

    }

    public int getOrderID() {
        return d_orderId;
    }

    public Player getOwnerPlayer() {
        return d_owner;
    }

    public boolean validate() {
        return false;
    }

}