package services;

import models.Continent;
import models.Country;
import models.GameState;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * This class is used to read the domination map
 * It has two methods parse and parseContinent
 * parse method is used to parse the file
 * parseContinent method is used to parse the continent
 */

public class DominationMapReader implements AdvanceGameMapReader{


    private final GameState d_gameState;
    private final Map<Integer, Continent> d_continents;
    private final Map<Integer, Country> d_countries;

    /**
     * Constructor for ConquestMapReader
     * @param dGameState GameState object
     */

    public DominationMapReader(GameState dGameState) {
        d_gameState = dGameState;
        d_continents = d_gameState.getContinents();
        d_countries = d_gameState.getCountries();
    }
    /**
     * This method is used to parse the file
     * @param p_filePath path of the file
     * @return true if the file is parsed successfully
     */
    @Override
    public boolean parse(String p_filePath) throws IOException {

        String l_line;
        boolean l_parsingContinents = false;
        boolean l_parsingCountries = false;
        boolean l_parsingConnections = false;
        int l_continentCount = 0;
        int l_countryCount = 0;

        try (BufferedReader l_reader = new BufferedReader(new FileReader(p_filePath))) {

            while ((l_line = l_reader.readLine()) != null) {
                l_line = l_line.trim();
                if (l_line.isEmpty() || l_line.startsWith(";")){
                    continue;
                }

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

        }

        return true;
    }

    /**
     * This method is used to parse the continent
     * @param p_line line to be parsed
     * @param p_continentCount count of the continent
     */

    @Override
    public void parseContinent(String p_line, int p_continentCount) {
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

}
