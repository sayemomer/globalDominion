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
    private int d_numberOfReinforcements;

    /**
     * Constructor for Country
     * has automatic name generation
     * sets d_adjacentCountries to an empty list
     *
     * @param p_countryId   Id of the country
     * @param p_continentId Id of the continent
     */
    public Country(int p_countryId, int p_continentId) {
        d_countryId = p_countryId;
        d_countryName = String.valueOf(p_countryId);
        d_continentId = p_continentId;
        d_adjacentCountries = new ArrayList<>();
        d_numberOfReinforcements = 0;
    }

    /**
     * Constructor for Country
     * sets d_adjacentCountries to an empty list
     *
     * @param p_countryId   Id of the country
     * @param p_countryName Name of the country
     * @param p_continentId Id of the continent
     */
    public Country(int p_countryId, String p_countryName, int p_continentId) {
        d_countryId = p_countryId;
        d_countryName = p_countryName;
        d_adjacentCountries = new ArrayList<>();
        d_continentId = p_continentId;
        d_numberOfReinforcements = 0;
    }

    /**
     * Constructor for Country
     *
     * @param p_countryId         Id of the country
     * @param p_countryName       Name of the country
     * @param p_continentId       Id of the continent
     * @param p_adjacentCountries List of adjacent countries
     */
    public Country(int p_countryId, String p_countryName, int p_continentId, List<Integer> p_adjacentCountries) {
        d_countryId = p_countryId;
        d_countryName = p_countryName;
        d_adjacentCountries = p_adjacentCountries;
        d_continentId = p_continentId;
    }

    /**
     * gets the number of reinforcements for current round.
     * @return total number of reinforcements for current round.
     */
    public int getNumberOfReinforcements() {
        return d_numberOfReinforcements;
    }

    /**
     * Set the number of reinforcements for current round.
     *
     * @param p_reinforcements number of reinforcements
     */
    public void setNumberOfReinforcements(int p_reinforcements) {
        d_numberOfReinforcements = p_reinforcements;
    }

    /**
     * get the country id
     * @return country id of the country
     */
    public int getCountryId() {
        return d_countryId;
    }

    /**
     * Set the country id
     *
     * @param p_countryId country id
     */
    public void setCountryId(int p_countryId) {
        this.d_countryId = p_countryId;
    }

    /**
     * get the country name
     * get the country name
     * @return country name
     */
    public String getName() {
        return d_countryName;
    }

    /**
     * Set the country name
     *
     * @param p_countryName country name
     */
    public void setName(String p_countryName) {
        this.d_countryName = p_countryName;
    }

    /**
     * get the list of adjacent countries
     * @return list of adjacent country ids
     */
    public List<Integer> getAdjacentCountries() {
        return d_adjacentCountries;
    }

    /**
     * adds adjacent country
     *
     * @param p_adjacentCountryId id of the adjacent country
     */
    public void addAdjacentCountry(int p_adjacentCountryId) {
        System.out.println("Adding adjacent country " + p_adjacentCountryId + " to " + this.d_countryId + this.d_countryName);
        this.d_adjacentCountries.add(p_adjacentCountryId);
    }

    /**
     * Remove adjacent country
     *
     * @param p_adjacentCountryId id of the adjacent country
     * @return true if removed successfully
     */
    public boolean removeAdjacentCountry(int p_adjacentCountryId) {
        return this.d_adjacentCountries.remove(new Integer(p_adjacentCountryId));
    }

    /**
     * get the continent id
     * @return continent id of the country
     */
    public int getContinentId() {
        return d_continentId;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryId=" + d_countryId +
                ", countryName='" + d_countryName + '\'' +
                ", continentId=" + d_continentId +
                '}';
    }

    /**
     * get the number of armies in the country
     * @return the number of armies in the country
     */

    public int getArmies() {
        return d_numberOfReinforcements;
    }

    /**
     * Set the continent id
     * @param p_continentId
     */

    public void setContinentId(int p_continentId) {
        this.d_continentId = p_continentId;
    }
}
