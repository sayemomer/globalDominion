package models;

import java.util.ArrayList;
import java.util.EnumSet;
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

    /**
     * This enum represents the game actions. used as flags to keep track of the game state.
     */
    public enum GameAction {
        VALID_MAP_LOADED, PlAYERS_ADDED, COUNTRIES_ASSIGNED,
    }

    private EnumSet<GameAction> gameStateFlags = EnumSet.noneOf(GameAction.class);

    public void setActionDone(GameAction action) {
        gameStateFlags.add(action);
    }

    public boolean isActionDone(GameAction action) {
        return gameStateFlags.contains(action);
    }

    public void removeAction(GameAction action) {
        gameStateFlags.remove(action);
    }


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

        //check if the map is loaded based on the gameState action
        if (!isActionDone(GameAction.VALID_MAP_LOADED)) {
            System.out.println("No map is loaded.");
            return;
        }

        // based on the gameState action , if the COUNTRIES_ASSIGNED is true then print the countries and continents
        // as per the players designated countries

        if (isActionDone(GameAction.COUNTRIES_ASSIGNED)) {

            for (Player l_player : getPlayers().values()) {

                // shows all countries and continents, armies on each country, ownership, and connectivity in a way that enables efficient game play
                //random different colors for each player
                System.out.println("Player: " + l_player.getName() + " (Reinforcements: " + l_player.getReinforcement() + ")");
                for (int l_countryID : l_player.getCountryIds()) {
                    Country l_country = countries.get(l_countryID);
                    System.out.print("CountryID: " + l_countryID +  " is connected to: ");
                    l_country.getAdjacentCountries().forEach(connectedId -> System.out.print(countries.get(connectedId).getCountryId() + "->"));
                    System.out.println(" (Reinforcements: " + l_country.getNumberOfReinforcements() + "])");
                    System.out.println(" (Continent ID: " + l_country.getContinentId() + "])");
                }

                System.out.println("=====================================");
            }
            return;
        }else {
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

        // print number of reinforcements for each player
        for (Player l_player : getPlayers().values()) {
            System.out.println(l_player.getName() + " has " + l_player.getReinforcement() + " reinforcements.");
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

