package models;

enum ORDERTYPE {Deploy}

public class Order {
    private int d_orderId;
    private int d_playerId;

    private ORDERTYPE d_orderType;
    private int d_timeStamp;

    public Order(int p_playerId, ORDERTYPE p_orderType) {
        this.d_playerId = p_playerId;
        this.d_orderType = p_orderType;
    }

    public Order() {

    }

    public void ExecuteOrder(Player p_player, ORDERTYPE p_orderType) {

    }

    public void ValidateOrder(Player p_player) {

    }

    public int getD_orderId() {
        return d_orderId;
    }

    public void setD_orderId(int p_orderId) {
        this.d_orderId = p_orderId;
    }

    public int getD_playerId() {
        return d_playerId;
    }


    public int getD_timeStamp() {
        return d_timeStamp;
    }

    public void setD_timeStamp(int p_timeStamp) {
        this.d_timeStamp = p_timeStamp;
    }
}
