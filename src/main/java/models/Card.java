package models;

/**
 * Card class to represent the card type
 * The card type can be "bomb", "blockade", "airlift", "negotiate"
 * The card type is randomly generated
 * The card type can be retrieved
 */


public enum Card {

    //"bomb", "blockade", "airlift", "negotiate"
    // TODO: change it to work for all the card later
    //BOMB, BLOCKADE, AIRLIFT, NEGOTIATE;
    NEGOTIATE;

    /**
     * Get a random card type
     * @return a random card type
     */
    public static Card getRandomCard() {
        return Card.values()[(int) (Math.random() * Card.values().length)];
    }

    /**
     * Get the card type
     * @return the card type
     */

    public String getCardType() {
        return this.name();
    }
}
