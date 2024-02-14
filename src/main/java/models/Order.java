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

    public void execute() {

    }


    public int getOrderID() {
        return d_orderId;
    }

    public int getD_playerId() {
        return d_playerId;
    }
    

}
