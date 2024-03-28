package models.behaviors;

import models.Country;
import models.Player;
import models.orders.DeployOrder;

import java.util.List;
import java.util.Map;

public class Aggressive extends PlayerBehavior{

    Country countryWithMaxReinforcements = null;


    public Aggressive(Player p_player, Map<Integer,Country> p_countries) {
        super(p_player, p_countries);
    }


    public void CountryWithMaxReinforcement() {
        //find a country with maximum number of reinforcement
        int maxReinforcements = Integer.MIN_VALUE;
        for (int i = 0; i < d_countries.size(); i++) {
            int reinforcements = d_countries.get(i).getNumberOfReinforcements();
            if (reinforcements > maxReinforcements) {
                maxReinforcements = reinforcements;
                countryWithMaxReinforcements = d_countries.get(i);
            }
        }
    }



    @Override
    public void issueOrders() {

    }

    @Override
    public Country toAttack() {
        return null;
    }

    @Override
    public Country toAttackFrom() {
        return null;
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
