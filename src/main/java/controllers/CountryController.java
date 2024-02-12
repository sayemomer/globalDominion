package controllers;

import java.util.*;

import models.Country;
import models.GameState;
import models.Player;

public class CountryController {
    GameState gameState;

    public CountryController(GameState p_gameState) {
        this.gameState = p_gameState;
    }

    /*
     * This method is used to handle to assign Countries to players.
     * @param p_args command arguments
     */

    public void handleAssignCountries() {
        ArrayList<Player> players = gameState.getPlayerList();
        Map<Integer, Country> countries = gameState.getGameMap().getCountries();

        try {
            if (countries.size() < players.size())
                throw new Exception("Invalid number of Countries.");

            Set<Integer> assignedCountries = new HashSet<>();

            while (assignedCountries.size() < countries.size()) {

                Random random = new Random();
                for (int i = 0; i < players.size(); i++) {
                    int x = random.nextInt(countries.size());
                    while (assignedCountries.contains(x)) {
                        x = random.nextInt(countries.size());
                    }
                    players.get(i).setCountries(countries.get(x));
                    assignedCountries.add(x);
                }
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
