package controllers;

import java.util.*;
import java.util.logging.Logger;

import config.Debug;
import models.Country;
import models.GameState;
import models.Player;
import models.Continent;
import services.CustomPrint;

/**
 * The CountryController class is responsible for handling country related commands.
 */
public class CountryController {
    GameState d_gameState;

    /**
     * Constructor for CountryController
     * @param p_gameState game state
     */

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

            CustomPrint.println("Countries assigned to players.");
            for (Player player : players.values()) {
                CustomPrint.println((player.getName() + " has " + player.getCountries().size() + " countries:"));
                for (Country country : player.getCountries().values()) {
                    CustomPrint.println("  " + country.getName());
                }
            }
            d_gameState.setActionDone(GameState.GameAction.COUNTRIES_ASSIGNED);
            d_gameState.assignReinforcements();
        } catch (Exception e) {
            CustomPrint.println(e.getMessage());
        }
    }


    /**
     * This method is used to handle the edit neighbor command.
     *
     * @param p_args command arguments
     */
    public void handleEditNeighborCommand(String[] p_args) {

        try {
            assert p_args.length % 3 == 0;
        } catch (Exception e) {
            CustomPrint.println("No command was executed. Invalid number of arguments. " + "Correct Syntax: \n\t" + Command.EDIT_CONTINENT_SYNTAX);
            return;
        }

        int argumentCount = 0;
        for (int i = 0; i < p_args.length; i++, argumentCount++) {
            Debug.log(String.valueOf(argumentCount));
            try {

                String l_option = p_args[i].toLowerCase();
                int l_countryID = Integer.parseInt(p_args[i + 1].toLowerCase());
                int l_neighborID = Integer.parseInt(p_args[i + 2].toLowerCase());
                i += 2;
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
                    CustomPrint.println("Added connection: " + l_country.getName() + " -> " + l_neighbor.getName());
                } else if (l_option.equals(Command.REMOVE)) {

                    if (l_country.removeAdjacentCountry(l_neighborID))
                        CustomPrint.println("Removed connection: " + l_country.getName() + " -> " + l_neighbor.getName());
                    else
                        throw new Exception("Connection does not exist.");
                } else {
                    throw new Exception("Invalid option. Correct Syntax: \n\t" + Command.EDIT_NEIGHBOR_SYNTAX);
                }
            } catch (NumberFormatException e) {
                CustomPrint.println("Error with EditNeighbor at index " + argumentCount + " : " + "Invalid country ID or neighbor ID.");
            } catch (Exception e) {
                CustomPrint.println("Error with EditNeighbor at index " + argumentCount + " : " + e.getMessage());
            }
        }
    }

    /**
     * This method is used to handle the edit country command.
     *
     * @param p_args command arguments
     */
    public void handleEditCountryCommand(String[] p_args) {

        try {
            for (int i = 0; i < p_args.length; i++) {
                Debug.log("p_args[" + i + "]: " + p_args[i]);
                if (p_args[i].equalsIgnoreCase("-add")) {
                    assert i + 3 <= p_args.length;

                    i += 2;
                } else if (p_args[i].equalsIgnoreCase("-remove")) {
                    assert i + 2 <= p_args.length;
                    i += 1;
                } else {
                    throw new Exception();
                }
            }
            Debug.log("handleEditCountryCommand: passed number of arguments check.");
        } catch (Exception | AssertionError e) {
            CustomPrint.println("No command was executed. Invalid number of arguments. " + "Correct Syntax: \n\t" + Command.EDIT_COUNTRY_SYNTAX);
            return;
        }
        int argumentCount = 0;
        for (int i = 0; i < p_args.length; i++, argumentCount++) {
            try {
                String l_option = p_args[i].toLowerCase();
                int l_countryId = Integer.parseInt(p_args[i + 1]);

                if (l_option.equals(Command.ADD)) {
                    int l_continentId = Integer.parseInt(p_args[i + 2]);
                    i += 2;
                    Country newCountry = new Country(l_countryId, l_continentId);
                    d_gameState.addCountry(newCountry);
                    CustomPrint.println("Added country: " + l_countryId + " to continent: " + l_continentId);
                }

                if (l_option.equalsIgnoreCase(Command.REMOVE)) {
                    i += 1;
                    if (!d_gameState.getCountries().containsKey(l_countryId))
                        throw new Exception("Country does not exist.");
                    removeRelatedConnectionsToCountry(l_countryId);
                    d_gameState.getCountries().remove(l_countryId);

                }

            } catch (NumberFormatException e) {
                CustomPrint.println("Error with EditCountry at index " + argumentCount + " : " + " Invalid country ID.");
            } catch (Exception e) {
                CustomPrint.println("Error with EditCountry at index " + argumentCount + " : " + e.getMessage());
            }

        }
    }

    /**
     * This method is used to handle the edit continent command.
     *
     * @param p_args command arguments
     */


    public void handleEditContinentCommand(String[] p_args) {

        try {
            for (int i = 0; i < p_args.length; i++) {
                Debug.log("p_args[" + i + "]: " + p_args[i]);
                if (p_args[i].equalsIgnoreCase("-add")) {
                    assert i + 3 <= p_args.length;

                    i += 2;
                } else if (p_args[i].equalsIgnoreCase("-remove")) {
                    assert i + 2 <= p_args.length;
                    i += 1;
                } else {
                    Debug.log("errrr");
                    throw new Exception();
                }
            }
            Debug.log("handleEditContinentCommand: passed number of arguments check.");
        } catch (Exception | AssertionError e) {
            CustomPrint.println("No command was executed. Invalid number of arguments. " + "Correct Syntax: \n\t" + Command.EDIT_CONTINENT_SYNTAX);
            return;
        }

        int argumentCount = 0;
        for (int i = 0; i < p_args.length; i++, argumentCount++) {
            try {

                String l_option = p_args[i].toLowerCase();

                if (l_option.equals(Command.ADD)) {
                    int l_continentId = Integer.parseInt(p_args[i + 1]);
                    int l_bonus = Integer.parseInt(p_args[i + 2]);
                    i += 2;
                    Continent newContinent = new Continent(l_continentId, l_bonus);
                    d_gameState.addContinent(newContinent);
                    CustomPrint.println("Added continent: " + newContinent);
                }

                if (p_args[i].equalsIgnoreCase(Command.REMOVE)) {
                    int l_continentId = Integer.parseInt(p_args[i + 1]);
                    i += 1;
                    d_gameState.removeContinent(l_continentId);
                    removeRelatedCountriesToContinent(l_continentId);
                }

            } catch (NumberFormatException e) {
                CustomPrint.println("Error with EditContinent at index " + argumentCount + " : " + "Invalid continent ID or bonus value.");
            } catch (Exception e) {
                CustomPrint.println("Error with EditContinent at index " + argumentCount + " : " + e.getMessage());
            }
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

        ArrayList<Continent> emptiedContinents = new ArrayList<>();

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
                emptiedContinents.add(continent);
            }
        }

        if (!coutriesToBeDeleted.isEmpty()) {
            outputString.append("Removed countries: ");
            coutriesToBeDeleted.forEach(countryId -> outputString.append(countryId).append(", "));
            outputString.append("\n");
        }

        if (!emptiedContinents.isEmpty()) {
            CustomPrint.print("WARNING: the following continents are empty: ");
            emptiedContinents.forEach(continent -> CustomPrint.print(" " + continent.getContinentId()));
            CustomPrint.println();
        }

        if (coutriesToBeDeleted.isEmpty()) {
            return;
        }

        CustomPrint.println(outputString);
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

        CustomPrint.println(outputString);
    }


}
