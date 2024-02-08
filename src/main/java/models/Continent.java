package models;

public class Continent {

    int continentId;
    String continentName;
    int continentValue;

    public Continent(int continentId, String continentName, int continentValue) {
        this.continentId = continentId;
        this.continentName = continentName;
        this.continentValue = continentValue;
    }

    int getBonusTroops() {

        return continentValue;
    };
}
