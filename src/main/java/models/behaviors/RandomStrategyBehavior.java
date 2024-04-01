//package models.behaviors;
//
//import models.Country;
//import models.GameState;
//import models.Player;
//import models.orders.AdvanceOrder;
//import models.orders.DeployOrder;
//import models.orders.Order;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//public class RandomStrategyBehavior extends StrategyBehavior {
//    // ConcreteStrategy of the Strategy pattern
//    Random rand = new Random();
//    GameState d_gameState;
//
//    public RandomStrategyBehavior(Player p_player, List<Country> p_countries) {
//        super(p_player, (Map<Integer, Country>) p_countries);
//    }
//
//    // Random country to move to
//    public Country getAttackTo() {
//        return(d_countries.get(rand.nextInt(d_countries.size() - 1)));
//    }
//
//    // Random of its own countries to defend
//    public Country getDeployIn() {
//        return d_player.getCountries().get(rand.nextInt(d_player.getCountries().size() - 1));
//    }
//
//    // Random of its own countries to attack from
//    public Country getAttackFrom() {
//        return getDeployIn();
//    }
//
//    // Random of its own countries to move from
//    public Country toMoveFrom() {
//        return getDeployIn();
//    }
//
//    // The Random player can either deploy or advance, determined randomly. .
//    public Order issueOtherOrders() {
//        int randomOrder = rand.nextInt(3);
//        int random_number_of_armies;
//        if (rand.nextInt(5) != 0) {
//            switch (randomOrder) {
//                case (0):
//                    return new DeployOrder(d_gameState,d_player, getDeployIn().getCountryId(), rand.nextInt(20));
//                case (1):
//                    return new AdvanceOrder(d_gameState,d_player, toMoveFrom().getCountryId(), getAttackTo().getCountryId(), rand.nextInt(toMoveFrom().getArmies() + 5));
//            }
//        }
//        return null;
//    }
//
//}
