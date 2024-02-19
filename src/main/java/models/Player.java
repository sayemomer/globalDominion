package models;

import controllers.OrderController;
import models.orders.DeployOrder;
import models.orders.Order;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Player {
    private String d_name;
    private int d_reinforcement;
    private int d_reinforcementPoll;
    private Queue<Order> d_orders;
    private ArrayList<Country> d_countries;

    public Player() {

    }

    /**
     * Constructor for the Player class
     *
     * @param p_name
     */
    public Player(String p_name) {
        d_name = p_name.toLowerCase();
        d_countries = new ArrayList<>();
        d_orders = new LinkedList<>();
        d_reinforcement = this.d_reinforcementPoll = 0;
    }

    /**
     * this method should not have any parameters and return value
     * adds an order to the list of orders when the game engine calls it during the issue orders phase.
     */
    public void issueOrder() {
        while (true) {
            Order l_order = OrderController.takeOrderCommands(this);
            if (l_order == null || !OrderController.validateOrder(l_order)) {
                continue;
            }
            d_orders.add(l_order);
            if (l_order instanceof DeployOrder) {
                reduceReinforcementPoll(((DeployOrder) l_order).getNumReinforcements());
                System.out.println(this.d_name + " has " + this.d_reinforcementPoll + " reinforcements left.");
            }
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

    public String getName() {
        return d_name;
    }

    public void setName(String p_name) {
        this.d_name = p_name;
    }

    public int getReinforcement() {
        return d_reinforcement;
    }

    /**
     * number of owned countries divided by 3, with a minimum of 3
     *
     * @return the base reinforcement for the player
     */
    public int getBaseReinforcement() {
        return Math.max(3, d_countries.size() / 3);
    }

    public void setReinforcement(int p_reinforcement) {
        this.d_reinforcement = this.d_reinforcementPoll = p_reinforcement;
    }

    public Queue<Order> getOrders() {
        return d_orders;
    }

    public ArrayList<Country> getCountries() {
        return d_countries;
    }

    /**
     * @return country ids of all the countries owned by the player
     */
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
        return "Player{" + d_name + '}';
    }

    public int getReinforcementPoll() {
        return d_reinforcementPoll;
    }

    public void reduceReinforcementPoll(int p_reinforcement) {
        d_reinforcementPoll -= p_reinforcement;
    }

}
