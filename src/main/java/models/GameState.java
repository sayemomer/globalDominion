package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The GameState class is responsible for keeping track of the game state.
 */


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

    public void setContinents(Map<Integer, Continent> p_continents) {
        continents = p_continents;
    }

    public Map<Integer, Country> getCountries() {
        return countries;
    }

    // set the countries
    public void setCountries(Map<Integer, Country> p_countries) {
        countries = p_countries;
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

    public void setPlayers(ArrayList<Player> p_players) {
        players = p_players;
    }

    public void showMap() {
    }

    /**
     * Prints the continents and their details.
     */
    public void printMap() {

        //check if the continets and countries are empty

        if (continents.isEmpty() && countries.isEmpty()) {
            System.out.println("No map loaded.");
            return;
        }

        System.out.println("Continents: ");
        continents.forEach((id, continent) -> System.out.println(id + ": " + continent.getContinentName() + " (Bonus: " + continent.getContinentValue() + ", Color: " + continent.getColor() + ")"));
        System.out.println("Countries: ");
        countries.forEach((id, country) -> {
            System.out.print("CountryID:" + id + " (" + country.getName() + ") is connected to: ");
            country.getAdjacentCountries().forEach(connectedId -> System.out.print(countries.get(connectedId).getName() + "->"));
            System.out.println(" (Continent ID: " + country.getContinentId() + "])");
        });
    }


}

