package models.behaviors;

import models.Country;
import models.GameState;
import models.Player;
import models.orders.DeployOrder;
import models.orders.Order;

import java.util.Map;

public class Benevolent extends PlayerBehavior{

    GameState d_gameState;
    Country weakestCountry;

    public Benevolent(Player p_player, Map<Integer, Country> p_countries) {
        super(p_player, p_countries);
    }

    public Country findWeakestCountry(){
        //find a country with minimum number of reinforcement
        int minReinforcements = Integer.MAX_VALUE;
        for (int i = 0; i < d_countries.size(); i++) {
            int reinforcements = d_countries.get(i).getNumberOfReinforcements();
            if (reinforcements < minReinforcements) {
                minReinforcements = reinforcements;
                weakestCountry = d_countries.get(i);
            }
        }
        return  weakestCountry;
    }

    @Override
    public Order issueOrders() {
        Order order = new DeployOrder(d_gameState, d_player, weakestCountry.getCountryId(), d_player.getReinforcement());
        return order;
    }

    /**
     * Benevolent player never attacks any country so it returns null
     * @return null because it never attacks any country
     */
    @Override
    public Country toAttack() {
        return null;
    }

    /**
     * Benevolent player never attack so it returns null
     * @return null because it never attacks
     */
    @Override
    public Country toAttackFrom() {
        return null;
    }

    @Override
    public Country toMoveFrom() {
        for(Integer countryId: weakestCountry.getAdjacentCountries()){
            if(d_player.getCountries().containsKey(countryId)) {
                int otherCountryReinforcement = d_gameState.getCountries().get(countryId).getNumberOfReinforcements();
                weakestCountry.setNumberOfReinforcements(otherCountryReinforcement - 1);
                d_gameState.getCountries().get(countryId).setNumberOfReinforcements(1);
            }
        }
        return null;
    }

    @Override
    public Country toDefend() {
        return null;
    }
}
