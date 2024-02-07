import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class Continent {
    String name;
    int bonus;
    String color;

    Continent(String name, int bonus, String color) {
        this.name = name;
        this.bonus = bonus;
        this.color = color;
    }
}

class Country {
    String name;
    int continentId;
    int x;
    int y;

    Country(String name, int continentId, int x, int y) {
        this.name = name;
        this.continentId = continentId;
        this.x = x;
        this.y = y;
    }
}

class MapParser {
    Map<Integer, Continent> continents = new HashMap<>();
    Map<Integer, Country> countries = new HashMap<>();

    public void parse(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        boolean parsingContinents = false;
        boolean parsingCountries = false;
        int continentCount = 0;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith(";")) continue;

            if (line.startsWith("[continents]")) {
                parsingContinents = true;
                parsingCountries = false;
                continue;
            } else if (line.startsWith("[countries]")) {
                parsingCountries = true;
                parsingContinents = false;
                continue;
            } else if (line.startsWith("[borders]")) {
                parsingContinents = false;
                parsingCountries = false;
                continue;
            }

            if (parsingContinents) {
                continentCount++;
                parseContinent(line, continentCount);
            } else if (parsingCountries) {
                parseCountry(line);
            }
        }

        reader.close();
    }

    private void parseContinent(String line, int continentCount) {
        String[] parts = line.split("\\s+");
        if (parts.length < 3) return; // Not enough parts for a valid continent line

        try {
            int id = continentCount;
            String name = parts[0].replace('_', ' ');
            int bonus = Integer.parseInt(parts[1]);
            String color = parts[2];
            continents.put(id, new Continent(name, bonus, color));
        } catch (NumberFormatException e) {
            System.err.println("Skipping line, unable to parse continent: " + line);
        }
    }

    private void parseCountry(String line) {
        String[] parts = line.split("\\s+");

        if (parts.length < 5) return; // Not enough parts for a valid country line

        try {
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            int continentId = Integer.parseInt(parts[2]);
            int x = Integer.parseInt(parts[3]);
            int y = Integer.parseInt(parts[4]);
            countries.put(id, new Country(name, continentId, x, y));
        } catch (NumberFormatException e) {
            System.err.println("Skipping line, unable to parse country: " + line);
        }
    }
}

public class MapReader {
    public static void main(String[] args) {

        String filePath = "/Users/sayems_mac/globalDominion/resources/canada.map";
        MapParser parser = new MapParser();

        try {
            parser.parse(filePath);
            System.out.println("Continents:");
            parser.continents.forEach((id, continent) ->
                    System.out.println(id + ": " + continent.name + " (Bonus: " + continent.bonus + ", Color: " + continent.color + ")"));

            System.out.println("\nCountries:");
            parser.countries.forEach((id, country) ->
                    System.out.println(id + ": " + country.name + " (Continent ID: " + country.continentId + ", Coordinates: [" + country.x + ", " + country.y + "])"));

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
