package models.orders;

import models.GameState;
import models.Player;
import models.Country;
import services.CustomPrint;

/**
 * DeployOrder class to deploy reinforcements to a country
 */
public class DeployOrder extends Order {
    private final int d_countryId;
    private final int d_numReinforcements;

    /**
     * Constructor for DeployOrder
     *
     * @param p_gameState         cannot be null
     * @param p_owner             cannot be null
     * @param p_countryId        cannot be null
     * @param p_numReinforcements cannot be null
     */
    public DeployOrder(GameState p_gameState, Player p_owner, int p_countryId, int p_numReinforcements) {
        super(p_gameState, p_owner);
        d_countryId = p_countryId;
        d_numReinforcements = p_numReinforcements;

        // if the deploy order is not valid an exception is thrown, which
        // fails the construction of the deploy order and as a result, any
        // code below this line will not get executed.
        validate();

        // the reinforcement poll is reduced only if the deploy order is valid.
        d_owner.reduceReinforcementPoll(d_numReinforcements);
        CustomPrint.println(d_owner.getName() + " has " + d_owner.getReinforcementPoll() + " reinforcements left.");
    }

    /**
     * This method does not have any parameters or return any value.
     */
    @Override
    public void execute() {
        Country country = d_gameState.getCountries().get(d_countryId);
        country.setNumberOfReinforcements(country.getNumberOfReinforcements() + d_numReinforcements);
    }

    @Override
    protected void validate() {
        if (!d_owner.getCountryIds().contains(d_countryId))
            throw new IllegalArgumentException("The given country is not owned by the player. Please try again.");

        if (d_numReinforcements < 0)
            throw new IllegalArgumentException("Number of reinforcements cannot be negative");

        if (d_gameState.getCountries().get(d_countryId) == null)
            throw new IllegalArgumentException("Country does not exist");

        if (d_owner.getReinforcementPoll() < d_numReinforcements)
            throw new IllegalArgumentException("Not enough reinforcements");
    }

    /**
     * get country id
     * @return country id
     */

    public int getCountryId() {
        return d_countryId;
    }


    /**
     * Retrieves the number of reinforcements available.
     *
     * @return The number of reinforcements.
     */
    public int getNumReinforcements() {
        return d_numReinforcements;
    }
    /**
     * Returns a string representation of the DeployOrder object.
     *
     * @return a string containing information about the owner, number of reinforcements deployed, and the country ID
     */
    @Override
    public String toString() {
        return "DeployOrder{" + d_owner + " deployed " + d_numReinforcements + " reinforcements to country " + d_countryId + "}";
    }

}
