package models;

import controllers.OrderController;
import models.orders.DeployOrder;
import models.orders.Order;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The Player class is responsible for keeping track of the player's state.
 */
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
     * this method should not have any parameters and does not return value
     * adds an order to the list of orders when the game engine calls it during the issue orders phase.
     */
    public void issueOrder() {
        while (true) {
            Order l_order = OrderController.takeOrderCommands(this);
            if (l_order != null) {
                d_orders.add(l_order);
                break;
            }
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

    /**
     * @return the name of the player
     */
    public String getName() {
        return d_name;
    }

    /**
     * sets the name of the player
     *
     * @param p_name
     */
    public void setName(String p_name) {
        this.d_name = p_name;
    }

    /**
     * @return number of reinforcements the player has
     */
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

    /**
     * sets the reinforcement for the player
     * updates reinforcementPoll as well
     *
     * @param p_reinforcement
     */
    public void setReinforcement(int p_reinforcement) {
        this.d_reinforcement = this.d_reinforcementPoll = p_reinforcement;
    }

    /**
     * @return the list of all the orders the player has
     */
    public Queue<Order> getOrders() {
        return d_orders;
    }

    /**
     * @return the list of all the countries the player owns
     */
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

    /**
     * adds a country to the list of countries owned by the player
     *
     * @param p_country
     */
    public void addCountry(Country p_country) {
        this.d_countries.add(p_country);
    }

    /**
     * removes all countries from the list of countries owned by the player
     */
    public void removeAllCountries() {
        this.d_countries.clear();
    }

    @Override
    public String toString() {
        return "Player{" + d_name + '}';
    }

    /**
     * @return reinforcementPoll
     */
    public int getReinforcementPoll() {
        return d_reinforcementPoll;
    }

    /**
     * @param p_reinforcement
     */
    public void reduceReinforcementPoll(int p_reinforcement) {
        d_reinforcementPoll -= p_reinforcement;
    }

}
