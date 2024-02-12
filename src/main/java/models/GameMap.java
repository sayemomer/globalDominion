package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameMap {
    Map<Integer, Continent> continents = new HashMap<>();
    Map<Integer, Country> countries = new HashMap<>();
    public GameMap(String path) {

    }

    public Map<Integer, Continent> getContinents() {
        return continents;
    }
    public Map<Integer, Country> getCountries() {
        return countries;
    }

    public GameMap() {

    }

    public boolean validateMap(){

        return true;
    }
    public void showMap(){

    }
    public void editMap(){

    }
    public void loadMap(){

    }

    public void saveMap(){

    }
}