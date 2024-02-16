package models.orders;

import models.Player;
import models.Country;


public class DeployOrder extends Order {
    int d_countryId;
    int d_numReinforcements;

    public DeployOrder(Player p_owner, int p_countryId, int p_numReinforcements) {
        super(p_owner);
        d_countryId = p_countryId;
        d_numReinforcements = p_numReinforcements;
    }

    public int getCountryId() {
        return d_countryId;
    }

    public int getNumReinforcements() {
        return d_numReinforcements;
    }

    @Override
    public void execute() {
        for (Country country : d_owner.getCountries()) {
            if (country.getCountryId() == d_countryId) {
                country.setNumberOfReinforcements(country.getNumberOfReinforcements() + d_numReinforcements);
            }
        }
    }

    @Override
    public String toString() {
        return "DeployOrder{" +
                "player" + d_owner +
                "d_countryId=" + d_countryId +
                ", d_numReinforcements=" + d_numReinforcements +
                '}';
    }
}
