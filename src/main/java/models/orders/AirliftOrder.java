package models.orders;

import models.GameState;
import models.Player;
import services.CustomPrint;

/**
 * This class is used to create an Airlift order. It is a subclass of Order class.
 * It is used to airlift the number of reinforcements from one country to another.
 * It has a method execute which is used to execute the order.
 * It has a method validate which is used to validate the order.
 * It has a method toString which is used to print the order.
 *
 */
public class AirliftOrder extends Order{

    private int d_countryFromId;
    private int d_countryToId;
    private int d_numReinforcements;

    /**
     * Constructor for the Airlift class
     * @param p_GameState the game state
     * @param p_OwnerPlayer the player who owns the airlift card
     * @param p_CountryFromId the country from which the player wants to airlift
     * @param p_CountryToId the country to which the player wants to airlift
     * @param p_NumReinforcements the number of reinforcements the player wants to airlift
     */

    public AirliftOrder(GameState p_GameState, Player p_OwnerPlayer,
                        int p_CountryFromId, int p_CountryToId, int p_NumReinforcements) {
        super(p_GameState, p_OwnerPlayer);
        d_countryFromId = p_CountryFromId;
        d_countryToId = p_CountryToId;
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
                d_gameState.getCountries().get(d_countryFromId).getName() + " to " + d_gameState.getCountries().get(d_countryToId).getName() + ".");
        //move the armies from the countryFromId to the countryToId
        d_gameState.getCountries().get(d_countryFromId).setNumberOfReinforcements(d_gameState.getCountries().get(d_countryFromId).getNumberOfReinforcements() - d_numReinforcements);
        d_gameState.getCountries().get(d_countryToId).setNumberOfReinforcements(d_gameState.getCountries().get(d_countryToId).getNumberOfReinforcements() + d_numReinforcements);

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

        //check if the countryFromId is owned by the player
        //check if the countryToId is owned by the player
        if (!d_owner.hasCountry(d_countryFromId) || !d_owner.hasCountry(d_countryToId)) {
            throw new IllegalArgumentException("The player does not own the country.");
        }

        //check if the countryFromId has enough armies to airlift
        if (d_gameState.getCountries().get(d_countryFromId).getNumberOfReinforcements() < d_numReinforcements) {
            throw new IllegalArgumentException("The country does not have enough armies to airlift.");
        }

    }
}
