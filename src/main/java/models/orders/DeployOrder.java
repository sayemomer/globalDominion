package models.orders;

import models.GameState;
import models.Player;
import models.Country;

/**
 * DeployOrder class to deploy reinforcements to a country
 */
public class DeployOrder extends Order {
    int d_countryId;
    int d_numReinforcements;

    /**
     * Constructor for DeployOrder
     *
     * @param p_gameState
     * @param p_countryId
     * @param p_numReinforcements
     */
    public DeployOrder(GameState p_gameState, int p_countryId, int p_numReinforcements) {
        super(p_gameState);
        d_countryId = p_countryId;
        d_numReinforcements = p_numReinforcements;
    }

    public int getCountryId() {
        return d_countryId;
    }

    public int getNumReinforcements() {
        return d_numReinforcements;
    }

    /**
     * This method does not have any parameters or return any value.
     */
    @Override
    public void execute() {
//        for (Country country : d_owner.getCountries()) {
//            if (country.getCountryId() == d_countryId) {
//                country.setNumberOfReinforcements(country.getNumberOfReinforcements() + d_numReinforcements);
//            }
//        }

        Country country = d_gameState.getCountries().get(d_countryId);
        country.setNumberOfReinforcements(country.getNumberOfReinforcements() + d_numReinforcements);
    }

    @Override
    public String toString() {
        return "DeployOrder{" +
                "player" + d_gameState.getPlayers() +
                "d_countryId=" + d_countryId +
                ", d_numReinforcements=" + d_numReinforcements +
                '}';
    }


}
