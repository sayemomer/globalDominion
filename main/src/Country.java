public class Country {
    private int countryId;
    private String countryName;
    private String[] adjacentCountries;

    public Country(int countryId, String countryName, String[] adjacentCountries) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.adjacentCountries = adjacentCountries;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String[] getAdjacentCountries() {
        return adjacentCountries;
    }

    public void setAdjacentCountries(String[] adjacentCountries) {
        this.adjacentCountries = adjacentCountries;
    }

    public void setPlayer() {}
    public void clearPlayer() {}
    public void editNeighbor() {}
    public static void main(String[] args) {


    }
}
