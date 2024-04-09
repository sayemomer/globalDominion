package models;

import controllers.OrderController;
import models.behaviors.HumanStrategyBehavior;
import models.behaviors.StrategyBehavior;
import models.orders.DeployOrder;
import models.orders.Order;
import services.CustomPrint;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * The Player class is responsible for keeping track of the player's state.
 */
public class Player implements Serializable {
    private String d_name;
    private int d_reinforcement;
    private int d_reinforcementPoll;
    private Queue<Order> d_orders;
    private Map<Integer, Country> d_countries;
    private StrategyBehavior d_strategy;
    private GameState d_gameState;

    //negotiated players
    private List<String> d_negotiatedPlayers = new ArrayList<>();

    //save the cards
    private ArrayList<Card> d_cards = new ArrayList<>();

    //only one card per turn
    private boolean d_cardFlag = false;

    /**
     * Constructor for the Player class
     */
    public Player() {

    }

    /**
     * Constructor for the Player class
     *
     * @param p_name the name of the player
     */
    public Player(String p_name) {
        d_name = p_name.toLowerCase();
        d_countries = new HashMap<>();
        d_orders = new LinkedList<>();
        d_reinforcement = this.d_reinforcementPoll = 0;
        d_strategy = new HumanStrategyBehavior(this, null);
    }

    /**
     * Constructor for the Player class
     * gets strategy class and constructs a new strategy object for the player
     * @param p_name the name of the player
     * @param p_gameState the game state
     * @param p_startegy the strategy class
     */
    public Player(String p_name, GameState p_gameState, Class<?> p_startegy) {
        d_name = p_name.toLowerCase();
        d_countries = new HashMap<>();
        d_orders = new LinkedList<>();
        d_reinforcement = this.d_reinforcementPoll = 0;
        d_gameState = p_gameState;
        try {
            d_strategy = (StrategyBehavior) p_startegy.getConstructor(Player.class, GameState.class).newInstance(this, d_gameState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this method should not have any parameters and does not return value
     * adds an order to the list of orders when the game engine calls it during the issue orders phase.
     */
    public void issueOrder() {
        Order order = d_strategy.issueOrder();
        if (order != null)
            d_orders.add(order);
    }

    /**
     * adds an order to the list of orders
     *
     * @param p_order the order to be added
     */

    public void setOrder(Order p_order) {
        d_orders.add(p_order);
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
     * gets the name of the player
     *
     * @return the name of the player
     */
    public String getName() {
        return d_name;
    }

    /**
     * sets the name of the player
     *
     * @param p_name the name of the player
     */
    public void setName(String p_name) {
        this.d_name = p_name;
    }

    /**
     * GEt the reinforcement for the player
     *
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
     * @param p_reinforcement the reinforcement number
     */
    public void setReinforcement(int p_reinforcement) {
        this.d_reinforcement = this.d_reinforcementPoll = p_reinforcement;
    }

    /**
     * Get the list of all the orders the player has
     *
     * @return the list of all the orders the player has
     */
    public Queue<Order> getOrders() {
        return d_orders;
    }

    /**
     * Get the list of all the countries the player owns
     *
     * @return the list of all the countries the player owns
     */
    public Map<Integer, Country> getCountries() {
        return d_countries;
    }

    /**
     * Get the country ID's
     *
     * @return country ids of all the countries owned by the player
     */
    public ArrayList<Integer> getCountryIds() {
        ArrayList<Integer> l_countryIds = new ArrayList<>();
        for (Country l_country : d_countries.values()) {
            l_countryIds.add(l_country.getCountryId());
        }
        return l_countryIds;
    }

    /**
     * adds a country to the list of countries owned by the player
     *
     * @param p_country the country to be added
     */
    public void addCountry(Country p_country) {
        this.d_countries.put(p_country.getCountryId(), p_country);
    }

    /**
     * removes a country from the list of countries owned by the player
     *
     * @param p_country the country to be removed
     */
    public void removeCountry(Country p_country) {
        d_countries.remove(p_country.getCountryId());
    }


    /**
     * removes all countries from the list of countries owned by the player
     */
    public void removeAllCountries() {
        this.d_countries.clear();
    }

    @Override
    public String toString() {
        return "Player{" + d_name + ", reinforcements: " + d_reinforcement + '}';
    }

    /**
     * get the reinforcement poll
     *
     * @return reinforcementPoll
     */
    public int getReinforcementPoll() {
        return d_reinforcementPoll;
    }

    /**
     * Reduce the reinforcement poll
     *
     * @param p_reinforcement the reinforcement to be reduced
     */
    public void reduceReinforcementPoll(int p_reinforcement) {
        d_reinforcementPoll -= p_reinforcement;
    }

    /**
     * void method to add a card to the player
     */
    public void addCard() {
        //check if the one card per turn is already taken
        if (!d_cardFlag) {
            //randomly select a card from the deck
            Card l_card = Card.getRandomCard();
            d_cards.add(l_card);
            CustomPrint.println("You have received a card: " + l_card);
            //set the flag to true
            d_cardFlag = true;
        }
    }

    /**
     * void method to add a card to the player
     *
     * @param p_card the card to be added
     */

    public void forecefullyAddCard(Card p_card) {
        d_cards.add(p_card);
    }

    /**
     * Get a random card from the player
     *
     * @return the cards
     */

    public String getcards() {
        StringBuilder l_cards = new StringBuilder();
        for (Card l_card : d_cards) {
            l_cards.append(l_card).append(", ");
        }
        return l_cards.toString();
    }

    /**
     * Check if the player has the card
     *
     * @param card the card
     * @return true if the player has the card
     */

    public boolean hasCard(String card) {
        for (Card l_card : d_cards) {
            if (l_card.toString().equals(card)) {
                return true;
            }
        }
        return false;
    }

    /**
     * remove the card from the player
     *
     * @param bomb remove the card from the player
     */

    public void removeCard(String bomb) {
        for (Card l_card : d_cards) {
            if (l_card.toString().equals(bomb)) {
                d_cards.remove(l_card);
                break;
            }
        }
    }

    /**
     * remove the card from the player
     */

    // remove all cards from the player
    public void removeAllCards() {
        d_cards.clear();
    }

    /**
     * Check if the player has the country
     *
     * @param p_CountryFromId the country id
     * @return true if the player has the country
     */

    public boolean hasCountry(int p_CountryFromId) {
        return d_countries.containsKey(p_CountryFromId);
    }


    /**
     * Add the player to the list of negotiated players
     *
     * @param p_PlayerToNegotiateWith add the player to the list of negotiated players
     */
    public void addNegotiatedPlayer(String p_PlayerToNegotiateWith) {
        d_negotiatedPlayers.add(p_PlayerToNegotiateWith);
    }

    /**
     * get the list of negotiated players
     *
     * @return the list of negotiated players
     */
    public List<String> getNegotiatedPlayers() {
        return d_negotiatedPlayers;
    }
}
