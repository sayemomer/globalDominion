public class Order {
    private int d_orderId;
    private int d_playerId;

    private enum d_orderType{Deploy};
    private int d_timeStamp;

    public void ExecuteOrder(Player p_player, d_orderType p_orderType){

    }

    public void ValidateOrder(Player p_player){

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
