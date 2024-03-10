package models.orders;

import models.Country;
import models.GameState;
import models.Player;

public class AdvanceOrder extends Order{
    private final int d_countryFromId;
    private final int d_countryToId;
    private final int d_numReinforcements;

    private static final double ATTACKER_KILL_PROBABILITY = 0.6;
    private static final double DEFENDER_KILL_PROBABILITY = 0.7;

    /**
     * Constructor for AdvanceOrder
     *
     * @param p_gameState         cannot be null
     * @param p_owner             cannot be null
     * @param p_countryFromId
     * @param p_countryToId
     * @param p_numReinforcements
     */
    public AdvanceOrder(GameState p_gameState, Player p_owner, int p_countryFromId, int p_countryToId, int p_numReinforcements) {
        super(p_gameState, p_owner);
        d_countryFromId = p_countryFromId;
        d_countryToId = p_countryToId;
        d_numReinforcements = p_numReinforcements;

        // if the Advance order is not valid an exception is thrown, which
        // fails the construction of the advance order and as a result, any
        // code below this line will not get executed.
        validate();

    }

    /**
     * This method does not have any parameters or return any value.
     */
    @Override
    public void execute() {
        if(d_owner.getCountries().get(d_countryToId) != null) {
            Country countryFrom = d_gameState.getCountries().get(d_countryFromId);
            Country countryTo = d_gameState.getCountries().get(d_countryToId);
            countryTo.setNumberOfReinforcements(countryTo.getNumberOfReinforcements() + d_numReinforcements);
            countryFrom.setNumberOfReinforcements(countryFrom.getNumberOfReinforcements() - d_numReinforcements);
        }
        else {


            int l_numReinforcementToCountry = d_gameState.getCountries().get(d_countryToId).getNumberOfReinforcements();
            int l_numReinforcementFromCountry = d_gameState.getCountries().get(d_countryFromId).getNumberOfReinforcements();
            int l_remainInToCountry = (int) (l_numReinforcementToCountry - Math.floor(60 / 100 * l_numReinforcementToCountry));
            int l_remainInFromCountry = (int) ((l_numReinforcementFromCountry) - Math.floor(70 / 100 * l_numReinforcementToCountry));


        }
    }

    @Override
    protected void validate() {

        if (!d_owner.getCountryIds().contains(d_countryFromId))
            throw new IllegalArgumentException("The given country is not owned by the player. Please try again.");

        if (!(d_owner.getCountries().get(d_countryFromId).getAdjacentCountries().contains(d_countryToId)))
            throw new IllegalArgumentException("The countries are not adjacent. Please try again.");

        if (d_numReinforcements < 0)
            throw new IllegalArgumentException("Number of reinforcements cannot be negative");

        if (d_gameState.getCountries().get(d_countryFromId) == null)
            throw new IllegalArgumentException("First country does not exist");

        if (d_gameState.getCountries().get(d_countryToId) == null)
            throw new IllegalArgumentException("Second country does not exist");

        if (d_owner.getCountries().get(d_countryFromId).getNumberOfReinforcements() < d_numReinforcements)
            throw new IllegalArgumentException("Not enough reinforcements");
    }

    @Override
    public String toString() {
        return "Advance Order{" + d_owner + " advanced + " + d_numReinforcements + " reinforcements to country " + d_countryToId + " from country " + d_countryFromId +" }";
    }

}
