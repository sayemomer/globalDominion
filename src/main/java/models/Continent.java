package models;

import java.io.Serializable;

/**
 * Continent class to store the continent details
 */
public class Continent implements Serializable {

    /**
     * Private field representing the ID of an object.
     * This ID is used to uniquely identify an instance of the object.
     * Access to this field is restricted to within the class it is declared in.
     */
    private int d_id;

    /**
     * Private field representing the value of an object.
     * This value holds a specific numerical or symbolic value relevant to the object's state or behavior.
     * Access to this field is restricted to within the class it is declared in.
     */
    private int d_value;

    /**
     * Private field representing the name of an object.
     * This name provides a human-readable identifier for the object.
     * Access to this field is restricted to within the class it is declared in.
     */
    private String d_name;
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
        this.d_id = p_continentId;
        this.d_name = p_continentName;
        this.d_value = p_continentValue;
        this.d_color = p_color;
    }

    /**
     * Constructor for Continent
     * sets the color to "N/A"
     * automatically sets the name to the id
     *
     * @param p_continentId   Id of the continent
     * @param p_continentValue Value of the continent
     */
    public Continent(int p_continentId, int p_continentValue) {
        this.d_id = p_continentId;
        this.d_name = "" + p_continentId;
        this.d_value = p_continentValue;
        this.d_color = "N/A";
    }

    /**
     * gets the continent id
     * @return continent id
     */
    public int getContinentId() {
        return d_id;
    }

    /**
     * gets the continent name
     * @return the name of the continent
     */
    public String getContinentName() {
        return d_name;
    }

    /**
     * gets the continent value
     * @return the bonus value of the continent
     */
    public int getContinentValue() {
        return d_value;
    }

    /**
     * sets the continent color
     * @return the color of the continent
     */
    public String getColor() {
        return d_color;
    }

    /**
     * sets the color of the continent
     *
     * @param p_color the color of the continent
     */
    public void setColor(String p_color) {
        this.d_color = p_color;
    }

    
    @Override
    public String toString() {
        return "Continent {" +
                "id=" + d_id +
                ", value=" + d_value +
                '}';
    }

}
