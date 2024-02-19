package models;

/**
 * Continent class to store the continent details
 */
public class Continent {

    private int d_id;
    private int d_value;
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
     * @param p_continentId
     * @param p_continentValue
     */
    public Continent(int p_continentId, int p_continentValue) {
        this.d_id = p_continentId;
        this.d_name = "" + p_continentId;
        this.d_value = p_continentValue;
        this.d_color = "N/A";
    }

    /**
     * @return continent id
     */
    public int getContinentId() {
        return d_id;
    }

    /**
     * @return the name of the continent
     */
    public String getContinentName() {
        return d_name;
    }

    /**
     * @return the bonus value of the continent
     */
    public int getContinentValue() {
        return d_value;
    }

    /**
     * @return the color of the continent
     */
    public String getColor() {
        return d_color;
    }

    /**
     * sets the color of the continent
     *
     * @param p_color
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
