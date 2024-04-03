package services;

import models.Continent;
import models.Country;
import models.GameState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The GameMapReader class is responsible for reading and parsing a game map file.
 * It reads the continents, countries, and their connections from the map file.
 */

public class GameMapReader implements Serializable {
    private final GameState d_gameState;
    private final Map<Integer, Continent> d_continents;
    private final Map<Integer, Country> d_countries;
//    private final Map<Integer, Continent> d_continents = new HashMap<>();
//    private final Map<Integer, Country> d_countries = new HashMap<>();


    /**
     * Constructor for the GameMapReader class.
     *
     * @param p_gameState The game state.
     */
    public GameMapReader(GameState p_gameState) {
        this.d_gameState = p_gameState;
        d_continents = d_gameState.getContinents();
        d_countries = d_gameState.getCountries();
    }

    /**
     * Constructor for the GameMapReader class.
     */

    public GameMapReader() {
        this.d_gameState = new GameState();
        d_continents = new HashMap<>();
        d_countries = new HashMap<>();
    }

    /**
     * Getter for the continents map.
     *
     * @return The continents map.
     */

    public Map<Integer, Continent> getContinents() {
        return d_continents;
    }

    /**
     * Getter for the countries map.
     *
     * @return The countries map.
     */

    public Map<Integer, Country> getCountries() {
        return d_countries;
    }

    /**
     * Parses the game map file and populates the continents, countries, and their connections.
     *
     * @param p_filePath The file path of the game map file.
     * @throws IOException If an I/O error occurs.
     * @return True if the map is valid, false otherwise.
     */
    public boolean parse(String p_filePath) throws IOException {
        BufferedReader l_reader = new BufferedReader(new FileReader(p_filePath));
        String l_line;
        boolean l_parsingContinents = false;
        boolean l_parsingCountries = false;
        boolean l_parsingConnections = false;
        int l_continentCount = 0;
        int l_countryCount = 0;

        while ((l_line = l_reader.readLine()) != null) {
            l_line = l_line.trim();
            if (l_line.isEmpty() || l_line.startsWith(";")) continue;

            if (l_line.startsWith("[continents]")) {
                l_parsingContinents = true;
                l_parsingCountries = false;
                continue;
            } else if (l_line.startsWith("[countries]")) {
                l_parsingCountries = true;
                l_parsingContinents = false;
                continue;
            } else if (l_line.startsWith("[borders]")) {
                l_parsingContinents = false;
                l_parsingCountries = false;
                l_parsingConnections = true;
                continue;
            }


            if (l_parsingContinents) {
                l_continentCount++;
                parseContinent(l_line, l_continentCount);
            } else if (l_parsingCountries) {
                l_countryCount++;
                parseCountry(l_line, l_countryCount);
            }
            if (l_parsingConnections) {
                parseConnection(l_line);
            }
        }

        l_reader.close();

        // validate the map after parsing
        if (!validateMap()) {
            d_gameState.removeAction(GameState.GameAction.VALID_MAP_LOADED);
            return false;
        } else {
            d_gameState.setActionDone(GameState.GameAction.VALID_MAP_LOADED);
            d_gameState.setContinents(d_continents);
            d_gameState.setCountries(d_countries);
            return true;
        }


    }

    /**
     * Parses a continent line and populates the continents map.
     *
     * @param p_line           The line containing the continent details.
     * @param p_continentCount The count of continents parsed so far.
     */

    private void parseContinent(String p_line, int p_continentCount) {
        String[] l_parts = p_line.split("\\s+");
        if (l_parts.length < 2) return; // Not enough parts for a valid continent line

        try {
            int id = p_continentCount;
            int bonus = Integer.parseInt(l_parts[1]);

            //if name and color are provided
            if (l_parts.length > 2) {
                String name = l_parts[0].replace('_', ' ');
                String color = l_parts[2];
                d_continents.put(id, new Continent(id, name, bonus, color));
            } else {
                d_continents.put(id, new Continent(id, bonus));
            }

        } catch (NumberFormatException e) {
            System.err.println("Skipping line, unable to parse continent: " + p_line);
        }
    }

    /**
     * Parses a country line and populates the countries map.
     *
     * @param p_line         The line containing the country details.
     * @param p_countryCount The count of countries parsed so far.
     */

    private void parseCountry(String p_line, int p_countryCount) {
        String[] l_parts = p_line.split("\\s+");

        if (l_parts.length < 3) return; // Not enough parts for a valid country line

        try {
            int l_id = Integer.parseInt(l_parts[0]);
            String l_name = l_parts[1];
            int l_continentId = Integer.parseInt(l_parts[2]);
            d_countries.put(l_id, new Country(l_id, l_name, l_continentId));
        } catch (NumberFormatException e) {

            System.err.println("Skipping line, unable to parse country: " + p_line);
        }
    }

    /**
     * Parses a connection line and populates the adjacent countries for each country.
     *
     * @param p_line The line containing the country connections.
     */

    private void parseConnection(String p_line) {
        String[] l_parts = p_line.split("\\s+");
        if (l_parts.length < 2) return; // Need at least two parts for a valid connection

        try {
            int countryId = Integer.parseInt(l_parts[0]);
            Country l_country = d_countries.get(countryId);
            if (l_country == null) {
                System.err.println("Skipping line, country ID not found: " + p_line);
                return;
            }
            for (int i = 1; i < l_parts.length; i++) {
                int l_connectedCountryId = Integer.parseInt(l_parts[i]);
                l_country.addAdjacentCountry(l_connectedCountryId);
            }
        } catch (NumberFormatException e) {
            System.err.println("Skipping line, unable to parse connection: " + p_line);
        }
    }

    /**
     * Prints the map rules.
     */
    public void printMapRules() {
        CustomPrint.println("Map Rules: ");
        CustomPrint.println("1. The map must contain at least one continent.");
        CustomPrint.println("2. Each continent must contain at least one country.");
        CustomPrint.println("3. Each country must have at least one connection.");
        CustomPrint.println("4. The map must be a connected graph.");
        CustomPrint.println("5. Each continent must be a connected subgraph.");
        CustomPrint.println("6. The map must not contain any self-loops.");
    }

    /**
     * Validates the map by checking if it is a connected graph and if each continent is a connected subgraph.
     *
     * @return True if the map is valid, false otherwise.
     */

    public boolean validateMap() {

        //FIXME: move this to GameGrapgUtils
        // check if the map has atleast one continent
        if (d_continents.isEmpty()) {
            System.err.println("The map does not contain any continents.");
            printMapRules();
            return false;
        }
        //FIXME: move this to GameGrapgUtils
        // check if the continents have atleast one country
        if (d_countries.isEmpty()) {
            System.err.println("The map does not contain any countries.");
            printMapRules();
            return false;
        }
        //FIXME: move this to GameGrapgUtils
        //check if the coutry has atleast one connection
        for (Country country : d_countries.values()) {
            if (country.getAdjacentCountries().isEmpty()) {
                System.err.println("The country '" + country.getCountryId() + "' does not have any connections.");
                printMapRules();
                return false;
            }
        }
        // Check if map is a connected graph
        GameGraphUtils l_graphUtils = new GameGraphUtils();
        if (!GameGraphUtils.isGraphConnected(d_countries)) {
            System.err.println("The map is a disconnected graph.");
            printMapRules();
            return false;
        }

        //check if the contries have self loop using l_graphUtils.isSelfLoop
        if (l_graphUtils.hasSelfLoop(d_countries)) {
            printMapRules();
            return false;
        }

        // Check if each continent is a connected subgraph
        for (Continent continent : d_continents.values()) {
            if (!GameGraphUtils.isContinentConnected(d_countries, continent.getContinentId())) {
                CustomPrint.println(String.valueOf(continent.getContinentId()));
                System.err.println("The continent '" + continent.getContinentName() + "' is a disconnected subgraph.");
                printMapRules();
                return false;
            }
        }


        // If all validations pass
        return true;
    }

}
