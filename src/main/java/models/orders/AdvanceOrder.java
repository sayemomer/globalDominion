package models.orders;

import models.Country;
import models.GameState;
import models.Player;

import java.util.Random;


/**
 * This class is used to create an Advance order. It is a subclass of Order class.
 * It is used to advance the number of reinforcements from one country to another.
 * It has a method execute which is used to execute the order.
 * It has a method validate which is used to validate the order.
 * It has a method toString which is used to print the order.
 */
public class AdvanceOrder extends Order{
    private final int d_countryAttackerId;
    private final int d_countryDefenderId;
    private final int d_numReinforcements;

    private static final double ATTACKER_BEING_KILLED = 0.7;
    private static final double DEFENDER_BEING_KILLED = 0.6;

    /**
     * Constructor for AdvanceOrder
     *
     * @param p_gameState         game state in which the order is being created
     * @param p_owner             player who is creating the order
     * @param p_countryFromId    From which country the player wants to advance
     * @param p_countryToId     To which country the player wants to advance
     * @param p_numReinforcements Number of reinforcements the player wants to advance
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
        Country attackerCountry = d_gameState.getCountries().get(d_countryAttackerId);
        Country defenderCountry = d_gameState.getCountries().get(d_countryDefenderId);

        if(d_owner.getCountries().get(d_countryDefenderId) != null) {
            defenderCountry.setNumberOfReinforcements(defenderCountry.getNumberOfReinforcements() + d_numReinforcements);
            attackerCountry.setNumberOfReinforcements(attackerCountry.getNumberOfReinforcements() - d_numReinforcements);
        }
        else {
            int l_numDefendingReinforcement = d_gameState.getCountries().get(d_countryDefenderId).getNumberOfReinforcements();
            int l_numAttackingReinforcement = d_numReinforcements;

            if(l_numDefendingReinforcement > l_numAttackingReinforcement){
                l_numDefendingReinforcement = l_numAttackingReinforcement;
            }

            int l_remainInAttackingCountry = attackerCountry.getNumberOfReinforcements() - d_numReinforcements;
            int l_remainInDefendingCountry = defenderCountry.getNumberOfReinforcements() - l_numDefendingReinforcement;

            Random random = new Random();
            int l_defenderKilled = 0;
            for (int i = 0; i < l_numDefendingReinforcement; i++) {
                if (random.nextDouble() <= DEFENDER_BEING_KILLED) {
                    l_defenderKilled++;
                }
            }

            int l_attackerKilled = 0;
            for (int i = 0; i < l_numAttackingReinforcement; i++) {
                if (random.nextDouble() <= ATTACKER_BEING_KILLED) {
                    l_attackerKilled++;
                }
            }

            defenderCountry.setNumberOfReinforcements(l_numDefendingReinforcement + l_remainInDefendingCountry - l_defenderKilled);

            if(defenderCountry.getNumberOfReinforcements() == 0 && l_numAttackingReinforcement != 0){
                 defenderCountry.setNumberOfReinforcements(l_numAttackingReinforcement - l_attackerKilled);
                 Player l_defender = d_gameState.getCountryOwner(d_countryDefenderId);
                 l_defender.removeCountry(defenderCountry);
                 d_owner.addCountry(defenderCountry);
                 //add card
                 d_owner.addCard();
            }
            else {
                attackerCountry.setNumberOfReinforcements(l_numAttackingReinforcement + l_remainInAttackingCountry);
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
            throw new IllegalArgumentException("Not enough reinforcements in country "+ d_countryAttackerId);

        //check if the player is negotiated with the player to attack
        if(d_gameState.getPlayers().get(d_owner.getName()).getNegotiatedPlayers().contains(d_gameState.getCountryOwner(d_countryDefenderId).getName())){
            throw new IllegalArgumentException("Player " + d_owner.getName() + " cannot attack " + d_gameState.getCountryOwner(d_countryDefenderId).getName() + " because they are negotiated.");
        }
    }

    @Override
    public String toString() {
        return "Advance Order{" + d_owner + " advanced + " + d_numReinforcements + " reinforcements to country " + d_countryDefenderId + " from country " + d_countryAttackerId +" }";
    }

}
