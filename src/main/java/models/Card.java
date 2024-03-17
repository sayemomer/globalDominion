package models;

/**
 * Card class to represent the card type
 * The card type can be "bomb", "blockade", "airlift", "negotiate"
 * The card type is randomly generated
 * The card type can be retrieved
 */


public enum Card {

    /**
     * The card type can be "bomb", "blockade", "airlift", "negotiate"
     * BOMB - bomb card
     * BLOCKADE - blockade card
     * AIRLIFT - airlift card
     * NEGOTIATE - negotiate card
     */

    /**
     * BOMB - bomb card
     */
    BOMB,
    /**
     * BLOCKADE - blockade card
     */
    BLOCKADE,
    /**
     * AIRLIFT - airlift card
     */
    AIRLIFT,
    /**
     * NEGOTIATE - negotiate card
     */
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
