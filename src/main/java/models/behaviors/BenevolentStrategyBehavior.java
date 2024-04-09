package models.behaviors;

import models.Country;
import models.GameState;
import models.Player;
import models.orders.AdvanceOrder;
import models.orders.DeployOrder;
import models.orders.Order;

/**
 * Represents a behavior implementing the Benevolent strategy for issuing orders in the game.
 * This strategy focuses on reinforcing the weakest countries and prioritizes strengthening vulnerable areas.
 */
public class BenevolentStrategyBehavior extends StrategyBehavior {

    /**
     * Constructs an instance of BenevolentStrategyBehavior.
     *
     * @param p_player    The player associated with this behavior.
     * @param p_gameState The current state of the game.
     */
    public BenevolentStrategyBehavior(Player p_player, GameState p_gameState) {
        super(p_player, p_gameState);
    }


    @Override
    public Order issueOtherOrders() {

        Country weakestCountry = findWeakestCountry();

        int maxReinforcements = Integer.MIN_VALUE;
        Country maxReinforcementsCountry = null;

        for (int adjacentId: weakestCountry.getAdjacentCountries()) {
            Country adjacentCountry = d_gameState.getCountries().get(adjacentId);
            if (d_player.getCountries().containsKey(adjacentId) && adjacentCountry.getNumberOfReinforcements() > maxReinforcements) {
                maxReinforcements = adjacentCountry.getNumberOfReinforcements();
                maxReinforcementsCountry = adjacentCountry;
            }
        }
        if(maxReinforcementsCountry != null) {
            return new AdvanceOrder(d_gameState, d_player, maxReinforcementsCountry.getCountryId(), weakestCountry.getCountryId(), maxReinforcements);
        }
            return null;
    }

    /**
     * The player deploy all it's reinforcement on it's weakest country
     * @return the deploy order
     */
    @Override
    protected Order issueDeployOrder() {
        Country weakestCountry = findWeakestCountry();
        return new DeployOrder(d_gameState, d_player, weakestCountry.getCountryId(), d_player.getReinforcement());
    }

    /**
     * Find the weakest country of player
     * @return the weakest country of the player
     */
    public Country findWeakestCountry(){
        Country weakestCountry = null;
        int minReinforcements = Integer.MAX_VALUE;
        for (Country country : d_player.getCountries().values()) {
            if (country.getNumberOfReinforcements() < minReinforcements) {
                minReinforcements = country.getNumberOfReinforcements();
                weakestCountry = country;
            }
        }
        return  weakestCountry;
    }
}
