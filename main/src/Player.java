public class Player {

    private int playerId;
    private String name;
    private int playerStat;
    private int reinforcement;
    private Order orders;
    private Country countries;

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
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerStat() {
        return playerStat;
    }

    public void setPlayerStat(int playerStat) {
        this.playerStat = playerStat;
    }

    public int getReinforcement() {
        return reinforcement;
    }

    public void setReinforcement(int reinforcement) {
        this.reinforcement = reinforcement;
    }

    public Order getOrders() {
        return orders;
    }

    public void setOrders(Order orders) {
        this.orders = orders;
    }

    public Country getCountries() {
        return countries;
    }

    public void setCountries(Country countries) {
        this.countries = countries;
    }


}
