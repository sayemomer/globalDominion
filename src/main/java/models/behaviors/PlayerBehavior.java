package models.behaviors;

import models.Country;
import models.Player;

import java.util.List;

public abstract class PlayerBehavior {
    Player d_player;
    List<Country> d_countries;

    public PlayerBehavior(Player p_player, List<Country> p_countries){
        d_player = p_player;
        d_countries = p_countries;
    }
    public abstract void issueOrders();
    public abstract Country toAttack();
    public abstract Country toAttackFrom();
    public abstract Country toMoveFrom();
    public abstract Country toDefend();

}
