public class Player {
    private int playerId;
    private String name;
    private int playerStat;
    private int reinforcement;
    private Order orders;
    private Country countries;

    public void IssueOrder(){
        //“issue_order()” (no parameters, no return value) whose function is to add an order to the list of orders held by the
        //player when the game engine calls it during the issue orders phase.

    }

    public Order NextOrder(){
        //“next_order()” (no parameters) method that is called by the GameEngine during the execute orders phase and
        //returns the first order in the player’s list of orders, then removes it from the list.
        Order o = new Order();
        return o;
    }


}
