package models;

import models.GameMap;
import models.Player;
import java.util.ArrayList;
import java.util.List;


public class GameState {
    private int currentTurn;
    private GameMap gameMap;
    private List<Player> playerList;

    public GameState() {

        this.playerList = new ArrayList<>();

    }


    public void advanceTurn() {


    }

    public void addPlayer(Player player) {

        playerList.add(player);
    }

    public void assignCountries() {
    }

    public void removePlayer(Player player) {
        playerList.remove(player);
    }

    public void showMap() {

    }


}

