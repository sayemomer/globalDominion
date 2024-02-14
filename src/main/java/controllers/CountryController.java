package controllers;

import java.util.*;

import config.AppConfig;
import models.Country;
import models.GameState;
import models.Player;
import models.Continent;


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

            for (Player player : players) {
                player.removeAllCountries();
            }
            AppConfig.log("Removed all countries from players.");
            
            List<Integer> countryIndices = new ArrayList<>(countries.keySet());
            Collections.shuffle(countryIndices);

            int playerIndex = 0;
            for (Integer index : countryIndices) {
                Player player = players.get(playerIndex);
                player.addCountry(countries.get(index));
                playerIndex = (playerIndex + 1) % players.size();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        AppConfig.log("Countries assigned to players.");
        for (Player player : players) {
            AppConfig.log(player.getName() + " has " + player.getCountries().size() + " countries.");
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

    /**
     * This method is used to handle the edit country command.
     *
     * @param p_args command arguments
     */
    public void handleEditCountryCommand(String[] p_args) {
        try {
            if (p_args.length != 3)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.EDIT_COUNTRY_SYNTAX);
            String l_option = p_args[0].toLowerCase();
            int l_countryId = Integer.parseInt(p_args[1]);
            int l_continentID = Integer.parseInt(p_args[2]);
            if (l_option.equals(Command.ADD)) {
                if (d_gameState.getCountries().containsKey(l_countryId))
                    throw new Exception("Country already exists.");
                if (!d_gameState.getContinents().containsKey(l_continentID))
                    throw new Exception("Continent does not exist.");

                d_gameState.getCountries().put(l_countryId, new Country(l_countryId, "", l_continentID));
                System.out.println("Added country: " + l_countryId + " to continent: " + l_continentID);
            } else if (l_option.equals(Command.REMOVE)) {
                if (!d_gameState.getCountries().containsKey(l_countryId))
                    throw new Exception("Country does not exist.");
                d_gameState.getCountries().remove(l_countryId);
                removeRelatedConnectionsToCountry(l_countryId);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is used to handle the edit continent command.
     *
     * @param p_args command arguments
     */


    public void handleEditContinentCommand(String[] p_args) {
        try {
            if (!(p_args.length == 3 || p_args.length == 2))
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.EDIT_CONTINENT_SYNTAX);
            String l_option = p_args[0].toLowerCase();
            int l_continentId = Integer.parseInt(p_args[1]);

            if (l_option.equals(Command.ADD)) {

                if (p_args.length != 3)
                    throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.EDIT_CONTINENT_SYNTAX);
                int l_bonus = Integer.parseInt(p_args[2]);
                if (d_gameState.getContinents().containsKey(l_continentId))
                    throw new Exception("Continent already exists.");
                d_gameState.getContinents().put(l_continentId, new Continent(l_continentId, l_bonus));
                System.out.println("Added continent: " + l_continentId + " with bonus: " + l_bonus);
                d_gameState.printMap();
                //TODO: handle the savemap command
            } else if (l_option.equals(Command.REMOVE)) {
                if (!d_gameState.getContinents().containsKey(l_continentId))
                    throw new Exception("Continent does not exist.");
                d_gameState.getContinents().remove(l_continentId);
                removeRelatedCountriesToContinent(l_continentId);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * This method is used to remove related countries to a continent.
     * uses removeRelatedConnectionsToCountry to remove connections to the country
     *
     * @param continentID continent ID
     */
    private void removeRelatedCountriesToContinent(int continentID) {
        StringBuilder outputString = new StringBuilder("As an effect to previous action, the following countries are removed:\n");
        ArrayList<Country> deletedCountries = new ArrayList<>();
        d_gameState.getCountries().forEach((id, country) -> {
            if (country.getContinentId() == continentID) {
                deletedCountries.add(d_gameState.getCountries().remove(id));
            }
        });
        outputString.append("Removed countries: ");
        deletedCountries.forEach(country -> outputString.append(country.getCountryId()).append(", "));
        outputString.append("\n");

        if (deletedCountries.isEmpty()) {
            return;
        }

        System.out.println(outputString);

        for (Country deletedCountry : deletedCountries) {
            removeRelatedConnectionsToCountry(deletedCountry.getCountryId());
        }
    }

    /**
     * This method is used to remove related connections to a country.
     * used in removeRelatedCountriesToContinent
     *
     * @param countryID country ID
     */
    private void removeRelatedConnectionsToCountry(int countryID) {
        StringBuilder outputString = new StringBuilder("As an effect to previous action, the following connections are removed:\n");
        Country deletedCountry = d_gameState.getCountries().get(countryID);
        d_gameState.getCountries().forEach((id, country) -> {
            if (country.getAdjacentCountries().contains(countryID)) {
                outputString.append(" ").append(country.getCountryId()).append(" -> ").append(deletedCountry.getCountryId()).append("\n");
                country.getAdjacentCountries().remove(countryID);
            }
        });

        System.out.println(outputString);
    }


}
