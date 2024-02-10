package controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import models.Country;
import models.Continent;

class MapParser {
    Map<Integer, Continent> continents = new HashMap<>();
    Map<Integer, Country> countries = new HashMap<>();

    public void parse(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        boolean parsingContinents = false;
        boolean parsingCountries = false;
        boolean parsingConnections = false;
        int continentCount = 0;
        int countryCount = 0;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith(";")) continue;

            if (line.startsWith("[continents]")) {
                parsingContinents = true;
                parsingCountries = false;
                continue;
            } else if (line.startsWith("[countries]")) {
                parsingCountries = true;
                parsingContinents = false;
                continue;
            } else if (line.startsWith("[borders]")) {
                parsingContinents = false;
                parsingCountries = false;
                parsingConnections = true;
                continue;
            }


            if (parsingContinents) {
                continentCount++;
                parseContinent(line, continentCount);
            } else if (parsingCountries) {
                countryCount++;
                parseCountry(line, countryCount);
            }
            if (parsingConnections) {
                parseConnection(line);
            }
        }

        reader.close();
    }

    private void parseContinent(String line, int continentCount) {
        String[] parts = line.split("\\s+");
        if (parts.length < 3) return; // Not enough parts for a valid continent line

        try {
            int id = continentCount;
            String name = parts[0].replace('_', ' ');
            int bonus = Integer.parseInt(parts[1]);
            String color = parts[2];
            continents.put(id, new Continent(id,name, bonus, color));
        } catch (NumberFormatException e) {
            System.err.println("Skipping line, unable to parse continent: " + line);
        }
    }

    private void parseCountry(String line,int countryCount) {
        String[] parts = line.split("\\s+");

        if (parts.length < 5) return; // Not enough parts for a valid country line

        try {
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            int continentId = Integer.parseInt(parts[2]);
            int x = Integer.parseInt(parts[3]);
            int y = Integer.parseInt(parts[4]);
            countries.put(id, new Country(id,name, continentId));
        } catch (NumberFormatException e) {
            System.err.println("Skipping line, unable to parse country: " + line);
        }
    }

    private void parseConnection(String line) {
        String[] parts = line.split("\\s+");
        if (parts.length < 2) return; // Need at least two parts for a valid connection

        try {
            int countryId = Integer.parseInt(parts[0]);
            Country country = countries.get(countryId);
            if (country == null) {
                System.err.println("Skipping line, country ID not found: " + line);
                return;
            }
            for (int i = 1; i < parts.length; i++) {
                int connectedCountryId = Integer.parseInt(parts[i]);
                country.setAdjacentCountries(connectedCountryId);
            }
        } catch (NumberFormatException e) {
            System.err.println("Skipping line, unable to parse connection: " + line);
        }
    }

    public boolean validateMap() {
        // Check if map is a connected graph
        if (!GraphUtils.isGraphConnected(countries)) {
            System.err.println("The map is a disconnected graph.");
            return false;
        }

        // Check if each continent is a connected subgraph
        for (Continent continent : continents.values()) {
            if (!GraphUtils.isContinentConnected(countries,continent.getContinentId())) {
                System.out.println(continent.getContinentId());
                System.err.println("The continent '" + continent.getContinentName() + "' is a disconnected subgraph.");
                return false;
            }
        }

        // Perform other custom validations as needed

        // If all validations pass
        return true;
    }

}

class GraphUtils {
    // Use DFS to check connectivity of the entire map
    public static boolean isGraphConnected(Map<Integer, Country> countries) {
        Set<Integer> visited = new HashSet<>();
        Integer start = countries.keySet().iterator().next(); // Start from any country
        dfs(start, countries, visited);
        return visited.size() == countries.size(); // If visited size equals the number of countries, graph is connected
    }

    // Use DFS to check connectivity of a continent's subgraph
    // Iterative DFS to check connectivity of a continent's subgraph
    public static boolean isContinentConnected(Map<Integer, Country> countries,int continentId) {
        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();

        // Find the first country in the continent to start the DFS
        for (Country country : countries.values()) {
            if (country.getContinentId() == continentId) {
                stack.push(country.getCountryId());
                break;
            }
        }

        if (stack.isEmpty()) {
            // No countries found in this continent, so it's disconnected by definition
            return false;
        }

        // Perform the iterative DFS
        while (!stack.isEmpty()) {
            int currentId = stack.pop();
            if (!visited.contains(currentId)) {
                visited.add(currentId);
                for (int neighborId : countries.get(currentId).getAdjacentCountries()) {
                    Country neighbor = countries.get(neighborId);
                    if (neighbor.getContinentId() == continentId && !visited.contains(neighborId)) {
                        stack.push(neighborId);
                    }
                }
            }
        }

        // Get the total number of countries in this continent
        int totalCountriesInContinent = 0;
        for (Country country : countries.values()) {
            if (country.getContinentId() == continentId) {
                totalCountriesInContinent++;
            }
        }

        // Compare the visited countries count with the total countries in the continent
        return visited.size() == totalCountriesInContinent;
    }

    private static void dfs(Integer countryId, Map<Integer, Country> countries, Set<Integer> visited) {
        visited.add(countryId);
        for (Integer connectedId : countries.get(countryId).getAdjacentCountries()) {
            if (!visited.contains(connectedId)) {
                dfs(connectedId, countries, visited);
            }
        }
    }
}

public class MapController {
    public static void main(String[] args) {

        String filePath = "./src/main/resources/canada.map";
        MapParser parser = new MapParser();

        try {
            parser.parse(filePath);

            if (parser.validateMap()) {
                System.out.println("Map validation passed.");
                // Proceed with other operations, e.g., displaying the map
            } else {
                System.err.println("Map validation failed.");
            }

            System.out.println("Continents:");
            parser.continents.forEach((id, continent) ->
                    System.out.println(id + ": " + continent.getContinentName() + " (Bonus: " + continent.getContinentValue() + ", Color: " + continent.getColor() + ")"));

            System.out.println("\nCountries:");
            parser.countries.forEach((id, country) -> {
                System.out.print("CountryID:" + id + " (" + country.getCountryName() + ") is connected to: ");
                country.getAdjacentCountries().forEach(connectedId -> System.out.print(parser.countries.get(connectedId).getCountryName()  + "->"));
                System.out.println( " (Continent ID: " + country.getContinentId() + "])");
            });

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
