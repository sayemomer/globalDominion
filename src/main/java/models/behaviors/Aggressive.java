package models.behaviors;

import models.Country;
import models.GameState;
import models.Player;
import models.orders.DeployOrder;
import models.orders.Order;

import java.util.List;
import java.util.Map;

public class Aggressive extends PlayerBehavior{

    Country countryWithMaxReinforcements = null;

    GameState d_gameState;


    public Aggressive(Player p_player, Map<Integer,Country> p_countries) {
        super(p_player, p_countries);
    }

    @Override
    public Order issueOrders() {
        //deploy on player's strongest country
        Order order = new DeployOrder(d_gameState, d_player, countryWithMaxReinforcements.getCountryId(), d_player.getReinforcement());
        return order;
    }

    @Override
    public Country toAttack() {
        return null;
    }

    @Override
    public Country toAttackFrom() {
        //find a country with maximum number of reinforcement
        int maxReinforcements = Integer.MIN_VALUE;
        for (int i = 0; i < d_countries.size(); i++) {
            int reinforcements = d_countries.get(i).getNumberOfReinforcements();
            if (reinforcements > maxReinforcements) {
                maxReinforcements = reinforcements;
                countryWithMaxReinforcements = d_countries.get(i);
            }
        }
        return  countryWithMaxReinforcements;
    }

    @Override
    public Country toMoveFrom() {
        return null;
    }

    @Override
    public Country toDefend() {
        return null;
    }
}
