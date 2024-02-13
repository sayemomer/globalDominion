package models;

/**
 * Continent class to store the continent details
 */

public class Continent {

    private int d_continentId;
    private String d_continentName;

    private int d_continentValue;
    private String d_color;

    /**
     * Constructor for Continent
     *
     * @param p_continentId    Id of the continent
     * @param p_continentName  Name of the continent
     * @param p_continentValue Value of the continent
     * @param p_color          Color of the continent
     */

    public Continent(int p_continentId, String p_continentName, int p_continentValue, String p_color) {
        this.d_continentId = p_continentId;
        this.d_continentName = p_continentName;
        this.d_continentValue = p_continentValue;
        this.d_color = p_color;
    }

    public Continent(int p_continentId, int p_continentValue) {
        this.d_continentId = p_continentId;
        this.d_continentName = "N/A";
        this.d_continentValue = p_continentValue;
        this.d_color = "N/A";
    }

    /**
     * Getters and Setters for the Continent class
     */

    public int getContinentId() {
        return d_continentId;
    }

    public void setContinentId(int p_continentId) {
        this.d_continentId = p_continentId;
    }

    public String getContinentName() {
        return d_continentName;
    }

    public void setContinentName(String p_continentName) {
        this.d_continentName = p_continentName;
    }

    public int getContinentValue() {
        return d_continentValue;
    }

    public void setContinentValue(int p_continentValue) {
        this.d_continentValue = p_continentValue;
    }

    public String getColor() {
        return d_color;
    }

    public void setColor(String p_color) {
        this.d_color = p_color;
    }
}
