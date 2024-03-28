package models.behaviors;

import models.Country;
import models.Player;
import models.orders.AdvanceOrder;
import models.orders.DeployOrder;
import models.orders.Order;
import phases.IssueDeployOrder;

import java.util.List;
import java.util.Map;

public abstract class PlayerBehavior {
    Player d_player;
    Map<Integer, Country> d_countries;

    public PlayerBehavior(Player p_player, Map<Integer,Country> p_countries){
        d_player = p_player;
        d_countries = p_countries;
    }
    public abstract Order issueOrders();
    public abstract Country toAttack();
    public abstract Country toAttackFrom();
    public abstract Country toMoveFrom();
    public abstract Country toDefend();

}
