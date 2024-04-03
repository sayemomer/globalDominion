package models.behaviors;

import models.Country;
import models.GameState;
import models.Player;
import models.orders.Order;

public class CheaterStrategyBehavior extends StrategyBehavior{

    public CheaterStrategyBehavior(Player p_player, GameState p_gameState) {
        super(p_player, p_gameState);
    }

    /**
     * conquers all the immediate neighboring enemy countries
     * doubles the number of armies on its countries that have enemy neighbors.
     * @return
     */
    @Override
    protected Order issueOtherOrders() {

        //for all the countries of the cheater player, find the enemy neighbors
        //then conquer all the enemy countries
        for(Country country: d_player.getCountries().values()){
            for(int adjCountryId: country.getAdjacentCountries()){
                if(!d_player.getCountries().containsKey(adjCountryId)){
                    Player defender = d_gameState.getCountryOwner(adjCountryId);
                    if(defender != null)
                        defender.removeCountry(d_gameState.getCountries().get(adjCountryId));
                    d_player.addCountry(d_gameState.getCountries().get(adjCountryId));

                    //reinforcement??????????
                }
            }
        }

        //double the number of armies in countries having enemy neighbor
        for(Country country: d_player.getCountries().values()){
            if(hasEnemy(country)){
                country.setNumberOfReinforcements(country.getNumberOfReinforcements() * 2);
            }
        }
        return null;
    }

    /**
     * This function checks whether a country has enemy in its neighbor
     * if the neighbor country has no owner, it does not consider an enemy
     * @param country the country that being checked
     * @return true or false showing that the country has enemy or not
     */
    public boolean hasEnemy(Country country){
        for(int adjCountryId: country.getAdjacentCountries()){
            if(!d_player.getCountries().containsKey(adjCountryId)){
                Player OwnerOfNeighbor = d_gameState.getCountryOwner(adjCountryId);
                if(OwnerOfNeighbor != null)
                    return true;
            }
        }
        return false;
    }

    /**
     * Cheater player do not deploy??
     * @return
     */
    @Override
    protected Order issueDeployOrder() {
        return null;
    }
}
