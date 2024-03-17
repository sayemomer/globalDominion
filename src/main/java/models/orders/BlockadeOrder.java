package models.orders;

import models.Country;
import models.GameState;
import models.Player;
import services.CustomPrint;

/**
 * Blockade class to represent the blockade order
 * The blockade order triples the number of armies on one of the player’s own territories and make it a neutral territory
 * The blockade order can be executed
 * The blockade order can be validated
 */

public class BlockadeOrder extends Order{

    private int l_countryId;


    /**
     * Constructor for the Blockade class
     * @param p_gameState the game state
     * @param p_owner the player who owns the blockade card
     * @param p_countryId the country id
     */

    public BlockadeOrder(GameState p_gameState, Player p_owner, int p_countryId) {
        super(p_gameState, p_owner);
        l_countryId = p_countryId;

        validate();
    }

    /**
     * Execute the blockade order
     * triples the number of armies on one of the player’s own territories and make it a neutral territory
     * remove the card from the player
     * add the country to the neutral player
     * remove the country from the current player
     *
     */
    @Override
    public void execute() {
        //triples the number of armies on one of the player’s own territories and make it a neutral territory
        CustomPrint.println("Player " + d_owner.getName() + " blockaded country " + d_gameState.getCountries().get(l_countryId).getName() +
                " and tripled the number of armies.");
        // check if the number of reinforcements is 0 and set it to 1
        d_gameState.getCountries().get(l_countryId).setNumberOfReinforcements(d_gameState.getCountries().get(l_countryId).getNumberOfReinforcements() == 0 ? 1
                : d_gameState.getCountries().get(l_countryId).getNumberOfReinforcements());
        d_gameState.getCountries().get(l_countryId).setNumberOfReinforcements(d_gameState.getCountries().get(l_countryId).getNumberOfReinforcements() * 3);

        // create a new neutral player and set the country owner to the neutral player
        d_gameState.addPlayer("Neutral");

        // add the country to the neutral player

        Country l_country = d_gameState.getCountries().get(l_countryId);
        d_gameState.getPlayers().get("Neutral").addCountry(l_country);

        // remove the country from the current player
        d_owner.removeCountry(l_country);


        // remove the card from the player
        d_owner.removeCard("BLOCKADE");

    }


    /**
     * Validate the blockade order
     * check if the player has the blockade card
     * check if the country is owned by the player
     * @throws IllegalArgumentException if the player does not have the blockade card or the country is not owned by the player
     */

    @Override
    protected void validate() {

        // check if he has the card
        if (!d_owner.hasCard("BLOCKADE")) {
            throw new IllegalArgumentException("Player " + d_owner.getName() + " does not have the blockade card.");
        }

        // cant not bloackade a country that is not owned by the player
        if (d_gameState.getCountryOwner(l_countryId) != d_owner) {
            throw new IllegalArgumentException("Player " + d_owner.getName() + " cannot blockade country " + d_gameState.getCountries().get(l_countryId).getName() + " because it is not owned by him.");
        }

    }
}
