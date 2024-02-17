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
    private Map<String, Player> players;
    private Map<Integer, Continent> continents;
    private Map<Integer, Country> countries;
    private String currentFileName;

    public GameState() {
        players = new HashMap<>();
        continents = new HashMap<>();
        countries = new HashMap<>();
        currentFileName = "";
    }

    public String getCurrentFileName() {
        return currentFileName;
    }

    public void setCurrentFileName(String p_currentFileName) {
        currentFileName = p_currentFileName;
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
        players.put(p_player.getName(), p_player);
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

    public Map<String, Player> getPlayers() {
        return players;
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

    /**
     * This method can be used at anytime after the countries are assigned to players.
     */
    public void assignReinforcements() {
        for (Player l_player : getPlayers().values()) {
            int l_additionalReinforcements = 0;
            ArrayList<Integer> l_ownedContinents = getPlayerOwnedContinents(l_player);
            for (int l_continentID : l_ownedContinents) {
                l_additionalReinforcements += continents.get(l_continentID).getContinentValue();
            }
            l_player.setReinforcement(l_player.getBaseReinforcement() + l_additionalReinforcements);
        }
    }

    /**
     * @param p_player
     * @return the list of all continent ids that are owned by the given player.
     */
    public ArrayList<Integer> getPlayerOwnedContinents(Player p_player) {
        ArrayList<Integer> l_continents = new ArrayList<>();
        for (int l_continentID : continents.keySet()) {
            boolean l_continentOwned = !p_player.getCountries().isEmpty();
            for (int l_countryID : getCountryIDsInsideContinent(l_continentID)) {
                if (!p_player.getCountryIds().contains(l_countryID)) {
                    l_continentOwned = false;
                    break;
                }
            }
            if (l_continentOwned) {
                l_continents.add(l_continentID);
            }
        }
        return l_continents;
    }

    /**
     * @param p_continentID
     * @return the list of all country ids that are inside the given continent.
     */
    public ArrayList<Integer> getCountryIDsInsideContinent(int p_continentID) {
        ArrayList<Integer> l_countries = new ArrayList<>();
        for (Map.Entry<Integer, Country> l_entry : countries.entrySet()) {
            if (l_entry.getValue().getContinentId() == p_continentID) {
                l_countries.add(l_entry.getKey());
            }
        }
        return l_countries;
    }

}

