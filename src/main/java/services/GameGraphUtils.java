package services;

import models.Country;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


/**
 * The GameGraphUtils class is responsible for providing utility methods to check the connectivity of the map graph.
 */

public class GameGraphUtils {

    /**
     * Checks if the graph is connected.
     *
     * @param p_countries The countries in the graph.
     * @return True if the graph is connected, false otherwise.
     */


    public static boolean isGraphConnected(Map<Integer, Country> p_countries) {

        // Use DFS to check connectivity of the graph
        Set<Integer> l_visited = new HashSet<>();
        Integer l_start = p_countries.keySet().iterator().next();
        dfs(l_start, p_countries, l_visited);
        return l_visited.size()==p_countries.size();
    }

    /**
     * Checks if the continent is connected.
     *
     * @param p_countries   The countries in the graph.
     * @param p_continentId The continent ID.
     * @return True if the continent is connected, false otherwise.
     */
    public static boolean isContinentConnected(Map<Integer, Country> p_countries, int p_continentId) {
        Set<Integer> l_visited = new HashSet<>();
        Stack<Integer> l_stack = new Stack<>();

        // Find the first country in the continent to start the DFS
        for (Country country : p_countries.values()) {
            if (country.getContinentId()==p_continentId) {
                l_stack.push(country.getCountryId());
                break;
            }
        }

        if (l_stack.isEmpty()) {
            // No countries found in this continent, so it's disconnected by definition
            return false;
        }

        // Perform the iterative DFS
        while (!l_stack.isEmpty()) {
            int currentId = l_stack.pop();
            if (!l_visited.contains(currentId)) {
                l_visited.add(currentId);
                for (int neighborId : p_countries.get(currentId).getAdjacentCountries()) {
                    Country neighbor = p_countries.get(neighborId);
                    if (neighbor.getContinentId()==p_continentId && !l_visited.contains(neighborId)) {
                        l_stack.push(neighborId);
                    }
                }
            }
        }

        // Get the total number of countries in this continent
        int totalCountriesInContinent = 0;
        for (Country country : p_countries.values()) {
            if (country.getContinentId()==p_continentId) {
                totalCountriesInContinent++;
            }
        }

        // Compare the visited countries count with the total countries in the continent
        return l_visited.size()==totalCountriesInContinent;
    }

    /**
     * Performs a depth-first search (DFS) on the graph.
     *
     * @param p_countryId The country ID to start the DFS.
     * @param p_countries The countries in the graph.
     * @param p_visited   The set of visited countries.
     * @return
     */

    public static boolean dfs(Integer p_countryId, Map<Integer, Country> p_countries, Set<Integer> p_visited) {
        p_visited.add(p_countryId);
        for (Integer l_connectedId : p_countries.get(p_countryId).getAdjacentCountries()) {
            if (!p_visited.contains(l_connectedId)) {
                dfs(l_connectedId, p_countries, p_visited);
            }
        }
        return false;
    }
}
