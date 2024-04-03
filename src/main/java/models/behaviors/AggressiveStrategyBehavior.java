package models.behaviors;

import models.Country;
import models.GameState;
import models.Player;
import models.orders.AdvanceOrder;
import models.orders.DeployOrder;
import models.orders.Order;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class AggressiveStrategyBehavior extends StrategyBehavior {

    public AggressiveStrategyBehavior(Player p_player, GameState p_gameState) {
        super(p_player, p_gameState);
    }

    /**
     * Always attacks with its strongest country,
     * then moves its armies in order to maximize aggregation of forces in one country.
     * If there is at least one enemy country adjacent to the strongest country,
     * it will attack the weakest of them. Otherwise, advances to the next strongest neighbor.
     *
     * @return
     */
    @Override
    public Order issueOtherOrders() {

        // TODO: USE CARDS IF AVAILABLE

        Country strongestCountry = findPlayersStrongestCountry();
        Map<Integer, Country> ownedCountries = d_player.getCountries();

        // check if the strongest country has an enemy neighbor
        boolean hasEnemyNeighbor = false;
        for (int adjacentId: strongestCountry.getAdjacentCountries())
            if (!ownedCountries.containsKey(adjacentId)) {
                hasEnemyNeighbor = true;
                break;
            }

        if (hasEnemyNeighbor) {
            // find the weakest enemy country
            int minReinforcements = Integer.MAX_VALUE;
            Country minReinforcementCountry = null;

            for (int adjacentId: strongestCountry.getAdjacentCountries()) {
                if (!ownedCountries.containsKey(adjacentId)) {
                    Country adjacentCountry = d_gameState.getCountries().get(adjacentId);
                    if (adjacentCountry.getNumberOfReinforcements() < minReinforcements) {
                        minReinforcements = adjacentCountry.getNumberOfReinforcements();
                        minReinforcementCountry = adjacentCountry;
                    }
                }
            }

            return new AdvanceOrder(d_gameState, d_player, strongestCountry.getCountryId(), minReinforcementCountry.getCountryId(), strongestCountry.getNumberOfReinforcements());
        }

        // if there is no enemy neighbor, move to the next strongest neighbor
        int maxReinforcements = Integer.MIN_VALUE;
        Country maxReinforcementsCountry = null;
        for (int adjacentId: strongestCountry.getAdjacentCountries()) {
            Country adjacentCountry = d_gameState.getCountries().get(adjacentId);
            if (adjacentCountry.getNumberOfReinforcements() > maxReinforcements) {
                maxReinforcements = adjacentCountry.getNumberOfReinforcements();
                maxReinforcementsCountry = adjacentCountry;
            }
        }

        return new AdvanceOrder(d_gameState, d_player, strongestCountry.getCountryId(), maxReinforcementsCountry.getCountryId(), strongestCountry.getNumberOfReinforcements());
    }

    @Override
    public Order issueDeployOrder() {
        Country strongestCountry = findPlayersStrongestCountry();
        return new DeployOrder(d_gameState, d_player, strongestCountry.getCountryId(), d_player.getReinforcementPoll());
    }

    /**
     * Find the strongest country of the player
     *
     * @return the strongest country of the player
     */
    private Country findPlayersStrongestCountry() {
        int maxReinforcements = Integer.MIN_VALUE;
        Country countryWithMaxReinforcements = null;
        System.out.println(d_player.getCountries().values());
        for (Country country : d_player.getCountries().values()) {
            if (country.getNumberOfReinforcements() > maxReinforcements) {
                maxReinforcements = country.getNumberOfReinforcements();
                countryWithMaxReinforcements = country;
            }
        }
        return countryWithMaxReinforcements;
    }
}
