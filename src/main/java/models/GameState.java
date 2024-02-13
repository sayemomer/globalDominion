package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GameState {
    private int currentTurn;
    private boolean mapLoaded = false;
    private ArrayList<Player> players;
    private Map<Integer, Continent> continents;
    private Map<Integer, Country> countries;

    public GameState() {
        players = new ArrayList<>();
        continents = new HashMap<>();
        countries = new HashMap<>();
    }

    public boolean isMapLoaded() {
        return mapLoaded;
    }

    public void setMapLoaded(boolean p_mapLoaded) {
        mapLoaded = p_mapLoaded;
    }
    public void set() {
        mapLoaded = true;
    }
    public void addPlayer(Player p_player) {
        players.add(p_player);
    }
    public Map<Integer, Continent> getContinents() {
        return continents;
    }
    public Map<Integer, Country> getCountries() {
        return countries;
    }

    public int advanceTurn() {
        currentTurn = (currentTurn + 1) % players.size();
        return currentTurn;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }


    public void showMap() {
    }
}

