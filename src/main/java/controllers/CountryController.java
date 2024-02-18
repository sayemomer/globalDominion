package controllers;

import java.sql.Array;
import java.util.*;

import config.AppConfig;
import config.Debug;
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

        Map<String, Player> players = d_gameState.getPlayers();
        Map<Integer, Country> countries = d_gameState.getCountries();

        try {
            if (p_args.length != 0)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.ASSIGN_COUNTRIES_SYNTAX);

            if (!d_gameState.isActionDone(GameState.GameAction.PlAYERS_ADDED))
                throw new Exception("No players were found. Add players before assigning countries.");
            if (!d_gameState.isActionDone(GameState.GameAction.VALID_MAP_LOADED))
                throw new Exception("No valid map is loaded. load a valid map before assigning countries.");

            // remove the previous action in case of reassigning countries
            d_gameState.removeAction(GameState.GameAction.COUNTRIES_ASSIGNED);

            if (countries.size() < players.size())
                throw new Exception("Invalid number of Countries.");

            for (Player player : players.values()) {
                player.removeAllCountries();
            }
            Debug.log("Removed all countries from players.");

            List<Integer> countryIndices = new ArrayList<>(countries.keySet());
            Collections.shuffle(countryIndices);

            ArrayList<String> playerKeySet = new ArrayList<>(players.keySet());

            int playerIndex = 0;
            for (Integer index : countryIndices) {
                Player player = players.get(playerKeySet.get(playerIndex));
                player.addCountry(countries.get(index));
                playerIndex = (playerIndex + 1) % playerKeySet.size();
            }

            System.out.println("Countries assigned to players.");
            for (Player player : players.values()) {
                Debug.log(player.getName() + " has " + player.getCountries().size() + " countries:");
                for (Country country : player.getCountries()) {
                    Debug.log("  " + country.getName());
                }
            }
            d_gameState.setActionDone(GameState.GameAction.COUNTRIES_ASSIGNED);
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

            if (l_country == null)
                throw new Exception("Country does not exist.");
            if (l_neighbor == null)
                throw new Exception("Neighbor does not exist.");

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
            if (!(p_args.length == 3 || p_args.length == 2))
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.EDIT_COUNTRY_SYNTAX);
            String l_option = p_args[0].toLowerCase();
            int l_countryId = Integer.parseInt(p_args[1]);
            int l_continentID = 0;
            if (p_args.length == 3)
                l_continentID = Integer.parseInt(p_args[2]);
            if (l_option.equals(Command.ADD)) {
                if (!(p_args.length == 3))
                    throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.EDIT_COUNTRY_SYNTAX);
                if (d_gameState.getCountries().containsKey(l_countryId))
                    throw new Exception("Country already exists.");
                if (!d_gameState.getContinents().containsKey(l_continentID))
                    throw new Exception("Continent does not exist.");

                d_gameState.getCountries().put(l_countryId, new Country(l_countryId, "", l_continentID));
                System.out.println("Added country: " + l_countryId + " to continent: " + l_continentID);
            } else if (l_option.equals(Command.REMOVE)) {
                if (!(p_args.length == 2))
                    throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.EDIT_COUNTRY_SYNTAX);
                if (!d_gameState.getCountries().containsKey(l_countryId))
                    throw new Exception("Country does not exist.");
                removeRelatedConnectionsToCountry(l_countryId);
                d_gameState.getCountries().remove(l_countryId);
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
        ArrayList<Integer> coutriesToBeDeleted = new ArrayList<>();

        for (Country country : d_gameState.getCountries().values()) {
            if (country.getContinentId() == continentID) {
                coutriesToBeDeleted.add(country.getCountryId());
            }
        }

        coutriesToBeDeleted.forEach(countryId -> removeRelatedConnectionsToCountry(countryId));
        coutriesToBeDeleted.forEach(countryId -> d_gameState.getCountries().remove(countryId));

        ArrayList<Continent> deletedContinents = new ArrayList<>();

        // remove empty countries
        for (Continent continent : d_gameState.getContinents().values()) {
            boolean foundCountry = false;
            for (Country country : d_gameState.getCountries().values()) {
                if (country.getContinentId() == continent.getContinentId()) {
                    foundCountry = true;
                    break;
                }
            }
            if (!foundCountry) {
                d_gameState.getContinents().remove(continent.getContinentId());
                deletedContinents.add(continent);
            }
        }

        outputString.append("Removed countries: ");
        coutriesToBeDeleted.forEach(countryId -> outputString.append(countryId).append(", "));
        outputString.append("\n");
        outputString.append("Removed continents: ");
        deletedContinents.forEach(continent -> outputString.append(continent.getContinentId()).append(", "));
        outputString.append("\n");

        if (coutriesToBeDeleted.isEmpty() && deletedContinents.isEmpty()) {
            return;
        }

        System.out.println(outputString);
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
                country.getAdjacentCountries().remove(new Integer(countryID));
            }
        });

        System.out.println(outputString);
    }


}
