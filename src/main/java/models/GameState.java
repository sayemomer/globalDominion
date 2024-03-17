package models;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * The GameState class is responsible for keeping track of the game state.
 */
public class GameState {
    private int d_currentTurn;
    private boolean d_mapLoaded = false;
    private Map<String, Player> d_players;
    private Map<Integer, Continent> d_continents;
    private Map<Integer, Country> d_countries;
    private String d_currentFileName;

    /**
     * adds a player to the game.
     *
     * @param p_name
     */

    public void addPlayer(String p_name) {
        d_players.put(p_name, new Player(p_name));
    }

    /**
     * This enum represents the game actions. used as flags to keep track of the game state.
     */
    public enum GameAction {
        VALID_MAP_LOADED, PlAYERS_ADDED, COUNTRIES_ASSIGNED,
    }

    private EnumSet<GameAction> d_gameStateFlags = EnumSet.noneOf(GameAction.class);

    /**
     * constructor for the GameState class.
     */
    public GameState() {
        d_players = new HashMap<>();
        d_continents = new HashMap<>();
        d_countries = new HashMap<>();
        d_currentFileName = "";
    }

    /**
     * adds the action to the set of actions that are done.
     *
     * @param p_action the action to add
     */
    public void setActionDone(GameAction p_action) {
        d_gameStateFlags.add(p_action);
    }

    /**
     * checks if the action is done.
     *
     * @param p_action the action to check
     * @return true if the action is done, false otherwise
     */
    public boolean isActionDone(GameAction p_action) {
        return d_gameStateFlags.contains(p_action);
    }

    /**
     * removes the action from the set of actions that are done.
     *
     * @param p_action the action to removes
     */
    public void removeAction(GameAction p_action) {
        d_gameStateFlags.remove(p_action);
    }

    /**
     * get the current filename
     *
     * @return current filename
     */
    public String getCurrentFileName() {
        return d_currentFileName;
    }

    /**
     * set the current filename
     *
     * @param p_currentFileName
     */
    public void setCurrentFileName(String p_currentFileName) {
        d_currentFileName = p_currentFileName;
    }

    /**
     * set the map loaded
     *
     * @param p_mapLoaded
     */
    public void setMapLoaded(boolean p_mapLoaded) {
        d_mapLoaded = p_mapLoaded;
    }

    /**
     * get continents
     *
     * @return all continents
     */
    public Map<Integer, Continent> getContinents() {
        return d_continents;
    }

    /**
     * sets the continents
     *
     * @param p_continents the continents to set
     */
    public void setContinents(Map<Integer, Continent> p_continents) {
        d_continents = p_continents;
    }

    /**
     * gets the countries
     *
     * @return all countries
     */
    public Map<Integer, Country> getCountries() {
        return d_countries;
    }

    /**
     * sets the countries
     *
     * @param p_countries the countries to set
     */
    public void setCountries(Map<Integer, Country> p_countries) {
        d_countries = p_countries;
    }

    /**
     * returns the current turn
     *
     * @return players
     */
    public Map<String, Player> getPlayers() {
        return d_players;
    }


    /**
     * Prints the continents and their details.
     */
    public void printMap() {
        //check if the map is loaded based on the gameState action
//        if (!isActionDone(GameAction.VALID_MAP_LOADED)) {
//            System.out.println("No map is loaded.");
//            return;
//        }
        if (d_continents.isEmpty()) {
            System.out.println("No continents are loaded.");
            return;
        }

        // based on the gameState action , if the COUNTRIES_ASSIGNED is true then print the countries and continents
        // as per the players designated countries

        if (isActionDone(GameAction.COUNTRIES_ASSIGNED)) {

            for (Player l_player : getPlayers().values()) {

                // shows all countries and continents, armies on each country, ownership, and connectivity in a way that enables efficient game play
                //random different colors for each player
                System.out.println("Player: " + l_player.getName() + " (Reinforcements: " + l_player.getReinforcementPoll() + ")" + " (Cards: " + l_player.getcards() + ")");
                for (int l_countryID : l_player.getCountryIds()) {
                    Country l_country = d_countries.get(l_countryID);
                    System.out.print("CountryID: " + l_countryID + " is connected to: ");
                    l_country.getAdjacentCountries().forEach(connectedId -> System.out.print(d_countries.get(connectedId).getCountryId() + "->"));
                    System.out.println(" (Reinforcements: " + l_country.getNumberOfReinforcements() + "])");
                    System.out.println(" (Continent ID: " + l_country.getContinentId() + "])");
                }

                System.out.println("=====================================");
            }
            return;
        } else {
            System.out.println("Continents: ");
            d_continents.forEach((id, continent) -> System.out.println(id + ": " + continent.getContinentName() + " (Bonus: " + continent.getContinentValue() + ", Color: " + continent.getColor() + ")"));
            System.out.println("Countries: ");
            d_countries.forEach((id, country) -> {
                System.out.print("CountryID:" + id + " (" + country.getName() + ") is connected to: ");
                country.getAdjacentCountries().forEach(connectedId -> System.out.print(d_countries.get(connectedId).getName() + "->"));
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
                l_additionalReinforcements += d_continents.get(l_continentID).getContinentValue();
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
        for (int l_continentID : d_continents.keySet()) {
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
        for (Map.Entry<Integer, Country> l_entry : d_countries.entrySet()) {
            if (l_entry.getValue().getContinentId() == p_continentID) {
                l_countries.add(l_entry.getKey());
            }
        }
        return l_countries;
    }

    /**
     * gets country's owner
     *
     * @param p_countryId
     * @return the player who owns the country
     */
    public Player getCountryOwner(int p_countryId) {
        for (Player player : d_players.values()) {
            if (player.getCountryIds().contains(p_countryId)) {
                return player;
            }
        }
        return null;
    }

    /**
     * This method returns the details of the players and their countries.
     *
     * @param gameState
     * @return playersDetails
     * such as the number of reinforcements, the number of cards, and the countries they own.
     */

    public HashMap<String, Object> getPlayerDetails(GameState gameState) {
        HashMap<String, Object> playersDetails = new HashMap<>();

        for (Player player : gameState.getPlayers().values()) {

            HashMap<Integer, HashMap<String, Object>> countriesDetails = new HashMap<>();

            for (int countryId : player.getCountryIds()) {
                Country country = gameState.getCountries().get(countryId);
                Continent continent = gameState.getContinents().get(country.getContinentId());

                HashMap<String, Object> countryDetails = new HashMap<>();
                countryDetails.put("ConnectedTo", country.getAdjacentCountries());
                countryDetails.put("Reinforcements", country.getNumberOfReinforcements());
                countryDetails.put("ContinentID", country.getContinentId());
                countryDetails.put("ContinentName", continent.getContinentName()); // Assuming you have a method to get continent name

                countriesDetails.put(countryId, countryDetails);
            }

            HashMap<String, Object> playerDetails = new HashMap<>();
            playerDetails.put("Reinforcements", player.getReinforcement());
            playerDetails.put("Card", 0); // Assuming a method to get player's card
            playerDetails.put("Countries", countriesDetails);

            playersDetails.put(player.getName(), playerDetails);
        }

        return playersDetails;
    }

    public void removeCountry(int p_countryId) throws Exception {
        if (d_countries.containsKey(p_countryId)) {
            d_countries.remove(p_countryId);
        } else {
            throw new Exception("Country does not exist.");
        }
    }

    public void removeContinent(int p_continentId) throws Exception {
        if (d_continents.containsKey(p_continentId)) {
            d_continents.remove(p_continentId);
        } else {
            throw new Exception("Continent ID " + p_continentId + " does not exist.");
        }
    }

    public void addContinent(Continent p_continent) throws Exception {
        if (d_continents.containsKey(p_continent.getContinentId())) {
            throw new Exception("Continent ID " + p_continent.getContinentId() + " already exists.");
        }
        d_continents.put(p_continent.getContinentId(), p_continent);
    }

    public void addCountry(Country p_country) throws Exception {
        if (d_countries.containsKey(p_country.getCountryId()))
            throw new Exception("Country " + p_country.getCountryId() + " already exists.");

        if (!d_continents.containsKey(p_country.getContinentId()))
            throw new Exception("Continent ID " + p_country.getContinentId() + " does not exist.");

        d_countries.put(p_country.getCountryId(), p_country);
    }


}

