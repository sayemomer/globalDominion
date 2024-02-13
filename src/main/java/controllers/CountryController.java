package controllers;

import java.util.*;

import models.Country;
import models.GameState;
import models.Player;

public class CountryController {
    GameState d_gameState;

    public CountryController(GameState p_gameState) {
        this.d_gameState = p_gameState;
    }

    private CountryController() {
    }

    /**
     * This method is used to handle the assign countries command.
     *
     * @param p_args command arguments
     */

    public void handleAssignCountriesCommand(String[] p_args) {
        // TODO: check if map is loaded before assigning countries

        ArrayList<Player> players = d_gameState.getPlayers();
        Map<Integer, Country> countries = d_gameState.getCountries();

        try {
            if (p_args.length != 0)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.ASSIGN_COUNTRIES_SYNTAX);

//            if (!d_gameState.isMapLoaded())
//                throw new Exception("Map is not loaded. Please load the map first.");

            if (countries.size() < players.size())
                throw new Exception("Invalid number of Countries.");

            Set<Integer> assignedCountries = new HashSet<>();

            while (assignedCountries.size() < countries.size()) {

                Random random = new Random();
                for (Player player : players) {
                    int x = random.nextInt(countries.size());
                    while (assignedCountries.contains(x)) {
                        x = random.nextInt(countries.size());
                    }
                    player.setCountries(countries.get(x));
                    assignedCountries.add(x);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is used to handle the edit neighbor command.
     *
     * @param p_args command arguments
     */
    public void handleEditNeighborCommand(String[] p_args) {
        try {
            if (p_args.length != 3)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.EDIT_NEIGHBOR_SYNTAX);

            String l_option = p_args[0].toLowerCase();
            int l_countryID = Integer.parseInt(p_args[1].toLowerCase());
            int l_neighborID = Integer.parseInt(p_args[2].toLowerCase());

            Country l_country = d_gameState.getCountries().get(l_countryID);
            Country l_neighbor = d_gameState.getCountries().get(l_neighborID);

            if (l_option.equals(Command.ADD)) {
                if (l_country.getAdjacentCountries().contains(l_neighborID))
                    throw new Exception("Connection already exists.");
                l_country.addAdjacentCountry(l_neighborID);
                System.out.println("Added connection: " + l_country.getName() + " -> " + l_neighbor.getName());
            } else if (l_option.equals(Command.REMOVE)) {
                if (l_country.removeAdjacentCountry(l_neighborID))
                    System.out.println("Removed connection: " + l_country.getName() + " -> " + l_neighbor.getName());
                else
                    throw new Exception("Connection does not exist.");
            } else {
                throw new Exception("Invalid option. Correct Syntax: \n\t" + Command.EDIT_NEIGHBOR_SYNTAX);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid country ID or neighbor ID.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
