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
 * This is the client class in the adapter pattern.
 */


public class GameMapReader implements Serializable {
    /**
     * The game state.
     */
    private final GameState d_gameState;
    /**
     * The continents map.
     */
    private final Map<Integer, Continent> d_continents;
    /**
     * The countries map.
     */
    private final Map<Integer, Country> d_countries;
    /**
     * The domination map type.
     */
    private static final String DOMINATION_MAP_TYPE = "Domination";
    /**
     * The conquest map type.
     */
    private static final String CONQUEST_MAP_TYPE = "Conquest";


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

        try (BufferedReader l_reader = new BufferedReader(new FileReader(p_filePath))) {
            String l_line;
            while ((l_line = l_reader.readLine())!=null) {
                l_line = l_line.trim();
                if (l_line.isEmpty() || l_line.startsWith(";")) {
                    continue;
                }

                if (l_line.startsWith("[continents]")) {
                    parseMap(p_filePath, DOMINATION_MAP_TYPE);
                }

                if (l_line.startsWith("[Map]")) {
                    parseMap(p_filePath, CONQUEST_MAP_TYPE);
                }
            }

            return postParseValidation();
        } catch (IOException e) {
            System.err.println("Error reading the map file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Parses the map based on the map type.
     *
     * @param p_filePath The file path of the game map file.
     * @param mapType The type of the map.
     * @throws IOException If an I/O error occurs.
     */

    private void parseMap(String p_filePath, String mapType) throws IOException {
        CustomPrint.println(mapType + " map detected");
        GameMapReaderAdapter l_gameMapReaderAdapter = new GameMapReaderAdapter(d_gameState, mapType);
        if (!l_gameMapReaderAdapter.parse(p_filePath)) {
            throw new IOException("Error parsing the map file");
        }
    }

    /**
     * Validates the map after parsing.
     *
     * @return True if the map is valid, false otherwise.
     */

    private boolean postParseValidation() {
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
     * Validates if the continents exist in the map.
     *
     * @return True if the continents exist, false otherwise.
     */

    private boolean validateContinentsExist() {
        if (d_continents.isEmpty()) {
            System.err.println("The map does not contain any continents.");
            printMapRules();
            return false;
        }
        return true;
    }

    /**
     * Validates if the countries exist in the map.
     *
     * @return True if the countries exist, false otherwise.
     */

    public boolean validateCountriesExist() {
        if (d_countries.isEmpty()) {
            System.err.println("The map does not contain any countries.");
            printMapRules();
            return false;
        }
        return true;
    }


    /**
     * Validates if the countries have connections.
     *
     * @return True if the countries have connections, false otherwise.
     */

    public boolean validateCountryConnections() {
        for (Country country : d_countries.values()) {
            if (country.getAdjacentCountries().isEmpty()) {
                System.err.println("The country '" + country.getCountryId() + "' does not have any connections.");
                printMapRules();
                return false;
            }
        }
        return true;
    }

    /**
     * Validates the graph connectivity.
     *
     * @return True if the graph is connected, false otherwise.
     */

    private boolean validateGraphConnectivity() {
        if (!GameGraphUtils.isGraphConnected(d_countries)) {
            System.err.println("The map is a disconnected graph.");
            printMapRules();
            return false;
        }
        return true;
    }

    /**
     * Validates the graph for self loops.
     *
     * @return True if the graph does not have self loops, false otherwise.
     */

    private boolean validateGraphSelfLoops() {
        if (GameGraphUtils.hasSelfLoop(d_countries)) {
            System.err.println("The map contains self-loops.");
            printMapRules();
            return false;
        }
        return true;

    }

    /**
     * Validates the continent connectivity.
     *
     * @return True if the continent is connected, false otherwise.
     */

    private boolean validateContinentConnectivity() {
        for (Continent continent : d_continents.values()) {
            if (!GameGraphUtils.isContinentConnected(d_countries, continent.getContinentId())) {
                System.err.println("The continent '" + continent.getContinentName() + "' is a disconnected subgraph.");
                printMapRules();
                return false;
            }
        }
        return true;
    }


    /**
     * Validates the map by checking if it is a connected graph and if each continent is a connected subgraph.
     *
     * @return True if the map is valid, false otherwise.
     */

    public boolean validateMap() {

        return validateContinentsExist() && validateCountriesExist() && validateCountryConnections() &&
                validateGraphConnectivity() && validateGraphSelfLoops() && validateContinentConnectivity();
    }

}
