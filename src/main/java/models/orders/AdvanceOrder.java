package models.orders;

import models.Country;
import models.GameState;
import models.Player;

import java.util.Random;

public class AdvanceOrder extends Order{
    private final int d_countryAttackerId;
    private final int d_countryDefenderId;
    private final int d_numReinforcements;

    private static final double ATTACKER_BEING_KILLED = 0.3;
    private static final double DEFENDER_BEING_KILLED = 0.4;

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
        d_countryAttackerId = p_countryFromId;
        d_countryDefenderId = p_countryToId;
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
        Country countryFrom = d_gameState.getCountries().get(d_countryAttackerId);
        Country countryTo = d_gameState.getCountries().get(d_countryDefenderId);

        if(d_owner.getCountries().get(d_countryDefenderId) != null) {
            countryTo.setNumberOfReinforcements(countryTo.getNumberOfReinforcements() + d_numReinforcements);
            countryFrom.setNumberOfReinforcements(countryFrom.getNumberOfReinforcements() - d_numReinforcements);
        }
        else {
            int l_numDefendingReinforcement = d_gameState.getCountries().get(d_countryDefenderId).getNumberOfReinforcements();
            int l_numAttackingReinforcement = d_numReinforcements;

            if(l_numDefendingReinforcement > l_numAttackingReinforcement){
                l_numDefendingReinforcement = l_numAttackingReinforcement;
            }

            int l_remainInAttackingCountry = countryFrom.getNumberOfReinforcements() - d_numReinforcements;
            int l_remainInDefendingCountry = countryTo.getNumberOfReinforcements() - l_numDefendingReinforcement;

            Random random = new Random();
            for (int i = 0; i < l_numDefendingReinforcement; i++) {
                if (random.nextDouble() <= DEFENDER_BEING_KILLED) {
                    l_numDefendingReinforcement--;
                }
            }

            for (int i = 0; i < l_numAttackingReinforcement; i++) {
                if (random.nextDouble() <= ATTACKER_BEING_KILLED) {
                    l_numAttackingReinforcement--;
                }
            }

            countryTo.setNumberOfReinforcements(l_numDefendingReinforcement + l_remainInDefendingCountry);

            if(countryTo.getNumberOfReinforcements() == 0 && l_numAttackingReinforcement != 0){
                 countryTo.setNumberOfReinforcements(l_numAttackingReinforcement);
                 d_owner.addCountry(countryTo);
                 //add card
            }
            else {
                countryFrom.setNumberOfReinforcements(l_numAttackingReinforcement + l_remainInAttackingCountry);
            }
        }
    }

    @Override
    protected void validate() {

        if (!d_owner.getCountryIds().contains(d_countryAttackerId))
            throw new IllegalArgumentException("The given country is not owned by the player. Please try again.");

        if (!(d_owner.getCountries().get(d_countryAttackerId).getAdjacentCountries().contains(d_countryDefenderId)))
            throw new IllegalArgumentException("The countries are not adjacent. Please try again.");

        if (d_numReinforcements < 0)
            throw new IllegalArgumentException("Number of reinforcements cannot be negative");

        if (d_gameState.getCountries().get(d_countryAttackerId) == null)
            throw new IllegalArgumentException("First country does not exist");

        if (d_gameState.getCountries().get(d_countryDefenderId) == null)
            throw new IllegalArgumentException("Second country does not exist");

        if (d_owner.getCountries().get(d_countryAttackerId).getNumberOfReinforcements() < d_numReinforcements)
            throw new IllegalArgumentException("Not enough reinforcements");
    }

    @Override
    public String toString() {
        return "Advance Order{" + d_owner + " advanced + " + d_numReinforcements + " reinforcements to country " + d_countryDefenderId + " from country " + d_countryAttackerId +" }";
    }

}
