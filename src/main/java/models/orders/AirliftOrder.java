package models.orders;

import models.Country;
import models.GameState;
import models.Player;
import services.CustomPrint;

import java.util.Random;

/**
 * This class is used to create an Airlift order. It is a subclass of Order class.
 * It is used to airlift the number of reinforcements from one country to another.
 * It has a method execute which is used to execute the order.
 * It has a method validate which is used to validate the order.
 * It has a method toString which is used to print the order.
 *
 */
public class AirliftOrder extends Order{

    private int d_countryAttackerId;
    private int d_countryDefenderId;
    private int d_numReinforcements;

    private static final double ATTACKER_BEING_KILLED = 0.7;
    private static final double DEFENDER_BEING_KILLED = 0.6;

    /**
     * Constructor for the Airlift class
     * @param p_GameState the game state
     * @param p_OwnerPlayer the player who owns the airlift card
     * @param p_CountryAttackerId the country from which the player wants to airlift
     * @param p_CountryDefenderId the country to which the player wants to airlift
     * @param p_NumReinforcements the number of reinforcements the player wants to airlift
     */

    public AirliftOrder(GameState p_GameState, Player p_OwnerPlayer,
                        int p_CountryAttackerId, int p_CountryDefenderId, int p_NumReinforcements) {
        super(p_GameState, p_OwnerPlayer);
        d_countryAttackerId = p_CountryAttackerId;
        d_countryDefenderId = p_CountryDefenderId;
        d_numReinforcements = p_NumReinforcements;
        validate();

    }

    /**
     * Execute the airlift order
     * move the armies from the countryFromId to the countryToId
     * remove the airlift card from the player
     *
     */

    @Override
    public void execute() {
        CustomPrint.println("Player " + d_owner.getName() + " airlifted " + d_numReinforcements + " armies from " +
                d_gameState.getCountries().get(d_countryAttackerId).getName() + " to " + d_gameState.getCountries().get(d_countryDefenderId).getName() + ".");

        Country attackerCountry = d_gameState.getCountries().get(d_countryAttackerId);
        Country defenderCountry = d_gameState.getCountries().get(d_countryDefenderId);
        Player defender = d_gameState.getCountryOwner(d_countryDefenderId);
        Player attacker = d_owner;

        if (d_gameState.getCountryOwner(d_countryDefenderId).equals(d_owner)) {
            defenderCountry.setNumberOfReinforcements(defenderCountry.getNumberOfReinforcements() + d_numReinforcements);
            attackerCountry.setNumberOfReinforcements(attackerCountry.getNumberOfReinforcements() - d_numReinforcements);
            return;
        }
        // if defending country doest have an owner or has 0 reinforcements
        if (defender == null || defenderCountry.getNumberOfReinforcements() == 0) {
            defenderCountry.setNumberOfReinforcements(d_numReinforcements);
            if (defender != null)
                defender.removeCountry(defenderCountry);
            d_owner.addCountry(defenderCountry);
            d_owner.addCard();
            return;
        }


        int attackingNumber = d_numReinforcements;
        int defendingNumber = Math.min(defenderCountry.getNumberOfReinforcements(), attackingNumber);

        // reduce reinforcements to send to battle
        int restingInAttackingCountry = attackerCountry.getNumberOfReinforcements() - attackingNumber;
        int restingInDefendingCountry = defenderCountry.getNumberOfReinforcements() - defendingNumber;

        defenderCountry.setNumberOfReinforcements(restingInDefendingCountry);
        attackerCountry.setNumberOfReinforcements(restingInAttackingCountry);

        // battle
        Random random = new Random();
        int l_defenderKilled = 0;
        for (int i = 0; i < defendingNumber; i++) {
            if (random.nextDouble() <= DEFENDER_BEING_KILLED) {
                l_defenderKilled++;
            }
        }

        int l_attackerKilled = 0;
        for (int i = 0; i < attackingNumber; i++) {
            if (random.nextDouble() <= ATTACKER_BEING_KILLED) {
                l_attackerKilled++;
            }
        }

        int remainingDefender = defendingNumber - l_defenderKilled;
        int remainingAttacker = attackingNumber - l_attackerKilled;

        // update ownership and the number of reinforcements
        if (remainingDefender == 0 && remainingAttacker > 0) {
            // transfer ownership
            defender.removeCountry(defenderCountry);
            attacker.addCountry(defenderCountry);
            attacker.addCard();
            // send remaining reinforcement
            defenderCountry.setNumberOfReinforcements(remainingAttacker);
        } else {
            defenderCountry.setNumberOfReinforcements(defenderCountry.getNumberOfReinforcements() + remainingDefender);
            attackerCountry.setNumberOfReinforcements(attackerCountry.getNumberOfReinforcements() + remainingAttacker);
        }

        //remove the airlift card from the player
        d_owner.removeCard("AIRLIFT");
    }

    /**
     * Validate the airlift order
     * check if the player has the airlift card
     * check if the countryFromId is owned by the player
     * check if the countryToId is owned by the player
     * check if the countryFromId has enough armies to airlift
     * @throws IllegalArgumentException if the validation fails
     *
     */

    @Override
    protected void validate() {

        // check if he has the airlift card
        if (!d_owner.hasCard("AIRLIFT")) {
            throw new IllegalArgumentException("The player does not have the airlift card.");
        }
        //check if the countryAttackerId is owned by the player
        if (!d_owner.hasCountry(d_countryAttackerId)) {
            throw new IllegalArgumentException("The player does not own the country.");
        }
        //check if the countryAttackerId has enough armies to airlift
        if (d_gameState.getCountries().get(d_countryAttackerId).getNumberOfReinforcements() < d_numReinforcements) {
            throw new IllegalArgumentException("The country does not have enough armies to airlift.");
        }
        if (d_numReinforcements < 0)
            throw new IllegalArgumentException("Number of reinforcements cannot be negative");

        if (d_gameState.getCountries().get(d_countryAttackerId) == null)
            throw new IllegalArgumentException("First country does not exist");

        if (d_gameState.getCountries().get(d_countryDefenderId) == null)
            throw new IllegalArgumentException("Second country does not exist");

        //check if the player is negotiated with the player to attack
        if (d_gameState.getPlayers().get(d_owner.getName()).getNegotiatedPlayers().contains(d_gameState.getCountryOwner(d_countryDefenderId).getName())) {
            throw new IllegalArgumentException("Player " + d_owner.getName() + " cannot attack " + d_gameState.getCountryOwner(d_countryDefenderId).getName() + " because they are negotiated.");
        }

    }
}
