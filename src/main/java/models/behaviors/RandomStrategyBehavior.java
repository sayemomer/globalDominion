package models.behaviors;

import models.Country;
import models.GameState;
import models.Player;
import models.orders.AdvanceOrder;
import models.orders.DeployOrder;
import models.orders.Order;

import java.util.*;

/**
 * RandomStrategyBehavior: computer player strategy that deploys on a random country, attacks random neighboring countries, and moves armies
 * randomly between its countries.
 */
public class RandomStrategyBehavior extends StrategyBehavior {
    // ConcreteStrategy of the Strategy pattern

    /**
     * generating random number
     */
    Random rand = new Random();

    /**
     * Constructor for RandomStrategyBehavior
     * @param p_player player
     * @param p_gameState gameState
     *
     */
    public RandomStrategyBehavior(Player p_player, GameState p_gameState) {
        super(p_player, p_gameState);
    }

    // The Random player can either deploy or advance, determined randomly. .
    /**
     * Randomly selects a country owned by the player and issues an advance order from that country
     * to one of its adjacent countries with a random number of armies.
     *
     * @return An AdvanceOrder object representing the order issued.
     */

    public Order issueOtherOrders() {
                    Map<Integer, Country> playerCountries = d_player.getCountries();
                    int randomPlayerCountryId = d_player.getCountryIds().get(rand.nextInt(d_player.getCountryIds().size()));
                    int randomAdjacentCountry = playerCountries.get(randomPlayerCountryId).getAdjacentCountries().get(rand.nextInt(playerCountries.get(randomPlayerCountryId).getAdjacentCountries().size()));
                    return new AdvanceOrder(d_gameState,d_player, randomPlayerCountryId , randomAdjacentCountry, d_player.getReinforcementPoll());
    }

    /**
     * Issues a deploy order for the player by randomly selecting one of the player's countries
     * and deploying a random number of armies to that country.
     *
     * @return A DeployOrder object representing the order issued.
     */

    @Override
    protected Order issueDeployOrder() {
        ArrayList<Integer> playerCountries =  d_player.getCountryIds();

        return new DeployOrder(d_gameState, d_player, playerCountries.get(rand.nextInt(playerCountries.size())), d_player.getReinforcementPoll());
    }

}
