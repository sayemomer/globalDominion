package services;

import models.Continent;
import models.Country;
import models.GameState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * This class is used to read the conquest map
 * It has two methods parse and parseContinent
 * parse method is used to parse the file
 * parseContinent method is used to parse the continent
 */

public class ConquestMapReader implements AdvanceGameMapReader{

    private final GameState d_gameState;
    private final Map<Integer, Continent> d_continents;
    private final Map<Integer, Country> d_countries;

    /**
     * Constructor for ConquestMapReader
     * @param dGameState GameState object
     */

    public ConquestMapReader(GameState dGameState) {
        d_gameState = dGameState;
        d_continents = d_gameState.getContinents();
        d_countries = d_gameState.getCountries();
    }

    /**
     * This method is used to parse the file
     * @param p_filePath path of the file
     * @return true if the file is parsed successfully
     * @throws IOException if there is an error in reading the file
     */

    @Override
    public boolean parse(String p_filePath) throws IOException {

        String l_line;
        boolean l_parsingContinents = false;
        boolean l_parsingTerritories = false;
        int l_continentCount = 0;

        try(BufferedReader l_reader = new BufferedReader(new FileReader(p_filePath))) {
            while ((l_line = l_reader.readLine()) != null) {
                l_line = l_line.trim();
                if (l_line.isEmpty() || l_line.startsWith(";")) continue;

                if (l_line.startsWith("[Continents]")) {
                    l_parsingContinents = true;
                    l_parsingTerritories = false;
                    continue;
                } else if (l_line.startsWith("[Territories]")) {
                    l_parsingTerritories = true;
                    l_parsingContinents = false;
                    continue;
                }


                if (l_parsingContinents) {
                    l_continentCount++;
                    parseContinent(l_line, l_continentCount);
                } else if (l_parsingTerritories) {
                    parseTerritory(l_line);
                }
            }
        }

        return true;
    }

    /**
     * This method is used to parse the territory
     * @param p_line line to be parsed
     */

    private void parseTerritory(String p_line) {

        String[] l_parts = p_line.split(",");
        if (l_parts.length < 5) return; // Check for minimum valid territory line

        try {
            String territoryName = l_parts[0];
            String continentName = l_parts[3];
            // Find the continent ID based on the name from the game state
            int continentId = d_continents.values().stream()
                    .filter(c -> c.getContinentName().equalsIgnoreCase(continentName))
                    .map(Continent::getContinentId)
                    .findFirst()
                    .orElse(-1);

            int territoryIdByName = d_countries.values().stream()
                    .filter(c -> c.getName().equalsIgnoreCase(territoryName))
                    .map(Country::getCountryId)
                    .findFirst()
                    .orElse(-1);

            boolean checkTerritoryExists = d_countries.values().stream()
                    .anyMatch(c -> c.getName().equalsIgnoreCase(territoryName));

            if (checkTerritoryExists && territoryIdByName != -1) {
                // update the continent ID for the territory
                d_countries.get(territoryIdByName).setContinentId(continentId);
                parseConnection(l_parts, continentId, d_countries.get(territoryIdByName) );
            } else {

                // Create a new country for the territory
                int territoryId = d_countries.size() + 1;
                Country territory = new Country(territoryId, territoryName, continentId);
                d_countries.put(territoryId, territory);
                parseConnection(l_parts, continentId, territory);

            }
        }
        catch(NumberFormatException e){
            System.err.println("Skipping line, unable to parse territory details: " + p_line);
        }
    }

    /**
     * This method is used to parse the continent
     * @param p_line line to be parsed
     * @param p_continentCount count of the continent
     */

    @Override
    public void parseContinent(String p_line, int p_continentCount) {

        String[] l_parts = p_line.split("=");
        if (l_parts.length != 2) return; // Not enough parts for a valid continent line

        try {
            String name = l_parts[0].trim();
            int bonus = Integer.parseInt(l_parts[1].trim());
            int id = p_continentCount; // Keep using the count as the ID for consistency

            System.out.println("Continent ID: " + id + " Name: " + name + " Bonus: " + bonus);

            d_continents.put(id, new Continent(id, name, bonus, "DefaultColor")); // Assuming color is not provided
        } catch (NumberFormatException e) {
            System.err.println("Skipping line, unable to parse continent bonus: " + p_line);
        }
    }

    /**
     * This method is used to parse the connection
     * @param p_parts parts of the line
     * @param continentId id of the continent
     * @param territory country object
     */

    public void parseConnection(String[] p_parts , int continentId, Country territory){

        for (int i = 4; i < p_parts.length; i++) {
            String adjacentTerritoryName = p_parts[i];

            boolean checkTerritoryExists = d_countries.values().stream()
                    .anyMatch(c -> c.getName().equalsIgnoreCase(adjacentTerritoryName));

            if (checkTerritoryExists) {

                int adjacentTerritoryId = d_countries.values().stream()
                        .filter(c -> c.getName().equalsIgnoreCase(adjacentTerritoryName))
                        .map(Country::getCountryId)
                        .findFirst()
                        .orElse(-1);
                territory.addAdjacentCountry(adjacentTerritoryId);
            } else {
                int adjacentTerritoryId = d_countries.size() + 1;
                Country adjacentTerritory = new Country(adjacentTerritoryId, adjacentTerritoryName, d_continents.get(continentId).getContinentId());
                System.out.println("Creating a new country from parseNewConnection: " + adjacentTerritoryId + " " + adjacentTerritory.getName() + " " + continentId);
                d_countries.put(adjacentTerritoryId, adjacentTerritory);
                territory.addAdjacentCountry((adjacentTerritoryId));
            }
        }

    }
}
