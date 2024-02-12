package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Country class to store the country details
 */

public class Country {
    private int d_countryId;
    private String d_countryName;
    private List<Integer> d_adjacentCountries;

    private int d_continentId;

    /**
     * Constructor for Country
     *
     * @param p_countryId   Id of the country
     * @param p_countryName Name of the country
     * @param p_continentId Id of the continent
     */

    public Country(int p_countryId, String p_countryName, int p_continentId) {
        this.d_countryId = p_countryId;
        this.d_countryName = p_countryName;
        this.d_adjacentCountries = new ArrayList<>();
        this.d_continentId = p_continentId;
    }

    /**
     * Getters and Setters for the Country class
     */

    public int getCountryId() {
        return d_countryId;
    }

    public void setCountryId(int p_countryId) {
        this.d_countryId = p_countryId;
    }

    public String getCountryName() {
        return d_countryName;
    }

    public void setCountryName(String p_countryName) {
        this.d_countryName = p_countryName;
    }

    public List<Integer> getAdjacentCountries() {
        return d_adjacentCountries;
    }

    public void setAdjacentCountries(int p_adjacentCountryId) {
        this.d_adjacentCountries.add(p_adjacentCountryId);
    }

    public int getContinentId() {
        return d_continentId;
    }

    public void setPlayer() {
    }

    public void clearPlayer() {
    }

    public void editNeighbor() {
    }

}
