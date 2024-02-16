package models;

import controllers.OrderController;
import models.orders.Order;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Player {

    private int d_playerId;
    private String d_name;
    private int d_playerStat;
    private int d_reinforcement;
    private int d_reinforcementPoll;
    private Queue<Order> d_orders;
    private ArrayList<Country> d_countries;

    public Player() {

    }

    public Player(int p_playerId, String p_name) {
        this.d_playerId = p_playerId;
        this.d_name = p_name.toLowerCase();
        this.d_countries = new ArrayList<>();
        this.d_orders = new LinkedList<>();
        this.d_reinforcement = this.d_reinforcementPoll = 0;
    }

    /**
     * this method should not have any parameters and return value
     * adds an order to the list of orders when the game engine calls it during the issue orders phase.
     */
    public void issueOrder() {
        while (true) {
            Order order = OrderController.takeOrderCommands(this);
            if (order == null || OrderController.validateOrder(order)) {
                continue;
            }
            d_orders.add(order);
            break;
        }
    }

    /**
     * this method should not have any parameters
     * it is called by the GameEngine during the execute orders phase
     *
     * @return the first order in the queue
     */
    public Order nextOrder() {
        return d_orders.poll();
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

    public int getBaseReinforcement() {
        return Math.max(3, d_countries.size() / 3);
    }

    public void setReinforcement(int p_reinforcement) {
        this.d_reinforcement = this.d_reinforcementPoll = p_reinforcement;
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

    public ArrayList<Integer> getCountryIds() {
        ArrayList<Integer> l_countryIds = new ArrayList<>();
        for (Country l_country : d_countries) {
            l_countryIds.add(l_country.getCountryId());
        }
        return l_countryIds;
    }

    public void addCountry(Country p_country) {
        this.d_countries.add(p_country);
    }

    public void removeAllCountries() {
        this.d_countries.clear();
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + d_playerId +
                ", name='" + d_name + '\'' +
                '}';
    }

    public int getReinforcementPoll() {
        return d_reinforcementPoll;
    }

    public void reduceReinforcementPoll(int p_reinforcement) {
        d_reinforcementPoll -= p_reinforcement;
    }


}
