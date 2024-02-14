package models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Player {

    private int d_playerId;
    private String d_name;
    private int d_playerStat;
    private int d_reinforcement;
    private Queue<Order> d_orders;
    private ArrayList<Country> d_countries;

    public Player() {

    }

    public Player(int p_playerId, String p_name) {
        this.d_playerId = p_playerId;
        this.d_name = p_name;
        this.d_countries = new ArrayList<>();
        this.d_orders = new LinkedList<>();

    }

    /**
     * this method should not have any parameters and return value
     * add an order to the list of orders when the game engine calls it during the issue orders phase.
     */
    public void IssueOrder() {
        //The GameEngine class calls the issue_order() method of the models.Player. This method will wait for the following
        //command, then create a deploy order object on the player’s list of orders, then reduce the number of armies in the
        //player’s reinforcement pool. The game engine does this for all players in round-robin fashion until all the players
        //have placed all their reinforcement armies on the map.
        //Issuing order command:
        //deploy countryID num (until all reinforcements have been placed)
        Order l_order = new Order(this.d_playerId, ORDERTYPE.Deploy);
        d_orders.add(l_order);

    }

    /**
     * this method should not have any parameters
     * it is called by the GameEngine during the execute orders phase
     *
     * @return the first order in the queue
     */
    public Order NextOrder() {
        Order order = d_orders.poll();
        return order;
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


    public Queue<Order> getOrders() {
        return d_orders;
    }

    public void setOrders(Order p_orders) {
        this.d_orders.add(p_orders);
    }

    public ArrayList<Country> getCountries() {
        return d_countries;
    }

    public void setCountries(Country p_countries) {
        this.d_countries.add(p_countries);
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + d_playerId +
                ", name='" + d_name + '\'' +
                '}';
    }
}
