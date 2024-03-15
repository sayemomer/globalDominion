package models.orders;

import models.GameState;
import models.Player;

public class DiplomacyOrder extends Order {

    private String d_playerToNegotiateWith;

    /**
     * Constructor for the Deplomacy class
     * @param p_gameState the game state
     * @param p_ownerPlayer the player who owns the deplomacy card
     * @param p_playerToNegotiateWith the player to deplomacy with
     */

    public DiplomacyOrder(GameState p_gameState, Player p_ownerPlayer, String p_playerToNegotiateWith) {
        super(p_gameState, p_ownerPlayer);
        d_playerToNegotiateWith = p_playerToNegotiateWith;

        validate();
    }

    /**
     * Execute the deplomacy order
     * remove the deplomacy card from the player
     * add the player to negotiate with to the negotiated players list
     * add the current player to the negotiated players list of the player to negotiate with
     *
     */

    @Override
    public void execute() {
        System.out.println("Player " + d_owner.getName() + " negotiated with " + d_playerToNegotiateWith + ".");
        d_owner.removeCard("NEGOTIATE");

        // add the player to negotiate with to the negotiated players list
        d_owner.addNegotiatedPlayer(d_playerToNegotiateWith);
        // add the current player to the negotiated players list of the player to negotiate with
        d_gameState.getPlayers().get(d_playerToNegotiateWith).addNegotiatedPlayer(d_owner.getName());

        // remove the deplomacy card from the player to negotiate with
        d_owner.removeCard("NEGOTIATE");

    }

    /**
     * Validate the deplomacy order
     * check if the player has the negotiate card
     * check if the player to negotiate with exists
     * check if the player to negotiate with is the current player
     * @throws IllegalArgumentException if the validation fails
     *
     */

    @Override
    protected void validate() {

        // check if the player has the negotiate card
    if (!d_owner.hasCard("NEGOTIATE")) {
            throw new IllegalArgumentException("Player " + d_owner.getName() + " does not have the deplomacy card.");
        }

        // check if the player to negotiate with exists
        if (!d_gameState.getPlayers().containsKey(d_playerToNegotiateWith)) {
            throw new IllegalArgumentException("Player " + d_playerToNegotiateWith + " does not exist.");
        }

        // check if the player to negotiate with is the current player
        if (d_playerToNegotiateWith.equals(d_owner.getName())) {
            throw new IllegalArgumentException("Cannot negotiate with yourself.");
        }

    }
}
