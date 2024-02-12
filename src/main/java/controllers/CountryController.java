package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import models.Country;
import models.Player;

public class CountryController {
        private ArrayList<Country> countries;
        private ArrayList<Player> players;
        public CountryController() {
            this.countries = new ArrayList<>();
            this.players =  new ArrayList<>();
        }
        

    
        
        /*
        * This method is used to handle to assgine Countries to players.
        * @param p_args command arguments
        */

        public void handleAssigneCountries() {
            Random random = new Random();
            try {
            if (countries.size() < players.size())
                throw new Exception("Invalid number of Countries.");

            
            Set<Integer> assignedCountries = new HashSet<>();
            
            while(assignedCountries.size() < countries.size()) {
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
