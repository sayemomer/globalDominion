package models;

import models.GameMap;
import models.Player;

import java.util.ArrayList;
import java.util.List;


public class GameState {
    private int currentTurn;
    private GameMap gameMap;
    private ArrayList<Player> playerList;

    public GameState() {
        playerList = new ArrayList<>();
    }

    public int advanceTurn() {
        currentTurn = (currentTurn + 1) % playerList.size();
        return currentTurn;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setMap(GameMap p_gameMap) {
        gameMap = p_gameMap;
    }

}

