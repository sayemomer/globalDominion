public class Player {

    private int d_playerId;
    private String d_name;
    private int d_playerStat;
    private int d_reinforcement;
    private Order d_orders;
    private Country d_countries;

    public void IssueOrder(){
        //“issue_order()” (no parameters, no return value) whose function is to add an order to the list of orders held by the
        //player when the game engine calls it during the issue orders phase.

    }

    public Order NextOrder(){
        //“next_order()” (no parameters) method that is called by the GameEngine during the execute orders phase and
        //returns the first order in the player’s list of orders, then removes it from the list.
        Order o = new Order();
        return o;
    }


    public int getPlayerId() {
        return d_playerId;
    }

    public void setPlayerId(int p_playerId) {
        this.d_playerId = p_playerId;
    }

    public String getName() {
        return d_name;
    }

    public void setName(String p_name) {
        this.d_name = p_name;
    }

    public int getPlayerStat() {
        return d_playerStat;
    }

    public void setPlayerStat(int p_playerStat) {
        this.d_playerStat = p_playerStat;
    }

    public int getReinforcement() {
        return d_reinforcement;
    }

    public void setReinforcement(int p_reinforcement) {
        this.d_reinforcement = p_reinforcement;
    }

    public Order getOrders() {
        return d_orders;
    }

    public void setOrders(Order p_orders) {
        this.d_orders = p_orders;
    }

    public Country getCountries() {
        return d_countries;
    }

    public void setCountries(Country p_countries) {
        this.d_countries = p_countries;
    }


}
