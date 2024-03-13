package models.orders;

import models.GameState;
import models.Player;

/**
 * Bomb class to represent the bomb order
 * The bomb order destroys half of the armies located on an opponent’s territory
 * The bomb order can be executed
 * The bomb order can be validated
 */

public class Bomb extends Order{


    /**
     * The country id
     */
    private int l_countryId;

    /**
     * Constructor for the Bomb class
     * @param p_gameState the game state
     * @param p_owner the player who owns the bomb card
     * @param p_countryId the country id
     */

    public Bomb(GameState p_gameState, Player p_owner, int p_countryId) {
        super(p_gameState, p_owner);
        l_countryId = p_countryId;

        //validate the bomb order
        validate();
    }

    /**
     * Execute the bomb order
     * Destroy half of the armies located on an opponent’s territory
     * Remove the card from the player
     * Print the action
     */

    @Override
    public void execute() {
        //destroy half of the armies located on an opponent’s territory
        System.out.println("Player " + d_owner.getName() + " bombed country " + d_gameState.getCountries().get(l_countryId).getName()
                + " owned by " + d_gameState.getCountryOwner(l_countryId).getName() +
                " and destroyed half of the armies.");

        int l_armies = d_gameState.getCountries().get(l_countryId).getNumberOfReinforcements() == 0 ? 1
                : d_gameState.getCountries().get(l_countryId).getNumberOfReinforcements();
        d_gameState.getCountries().get(l_countryId).setNumberOfReinforcements((int) Math.floor(l_armies / 2));

        //remove the card from the player
        d_owner.removeCard("BOMB");

    }

    /**
     * Validate the bomb order
     * The bomb order cannot be executed if the player does not have a bomb card
     * The bomb order cannot be executed if the player owns the country
     * @throws IllegalArgumentException if the bomb order cannot be executed
     */

    @Override
    protected void validate() {
        // cannot bomb a country that is owned by the player
        if (d_owner.getCountries().containsKey(l_countryId)) {
            throw new IllegalArgumentException("Cannot bomb a country that is owned by the player.");
        }

        // check if he has a card
        if (!d_owner.hasCard("BOMB")) {
            throw new IllegalArgumentException("Player does not have a bomb card.");
        }
    }
}
