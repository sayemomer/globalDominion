package models;
public class Country {
    private int d_countryId;
    private String d_countryName;
    private String[] d_adjacentCountries;

    public Country(int p_countryId, String p_countryName, String[] p_adjacentCountries) {
        this.countryId = p_countryId;
        this.countryName = p_countryName;
        this.adjacentCountries = p_adjacentCountries;
    }

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

    public String[] getAdjacentCountries() {
        return d_adjacentCountries;
    }

    public void setAdjacentCountries(String[] p_adjacentCountries) {
        this.d_adjacentCountries = p_adjacentCountries;
    }

    public void setPlayer() {}
    public void clearPlayer() {}
    public void editNeighbor() {}
    public static void main(String[] args) {


    }
}
