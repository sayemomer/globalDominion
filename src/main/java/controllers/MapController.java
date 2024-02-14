package controllers;

import models.Continent;
import models.Country;
import models.GameState;
import services.GameMapReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * The MapController class is responsible for handling the map parsing,
 * validation, and display of continents and countries from a game map file.
 * It utilizes the GameMapReader service to read and validate the map file.
 */

public class MapController {

    private final GameMapReader d_gameMapReader;
    GameState gameState;
    private String d_filePath;

    /**
     * Constructor for the MapController class.
     *
     * @param gameState The game state.
     */

    public MapController(GameState gameState, String d_filePath) {
        this.d_gameMapReader = new GameMapReader(gameState);
        this.d_filePath = d_filePath;
        this.gameState = gameState;
    }


    public MapController(GameState gameState) {
        this.d_gameMapReader = new GameMapReader(gameState);
        this.d_filePath = "";
        this.gameState = gameState;
    }

    public MapController(String d_filePath) {
        this.gameState = new GameState();
        this.d_gameMapReader = new GameMapReader(gameState);
        this.d_filePath = d_filePath;
    }

    /**
     * Main method to test the MapController class.
     *
     * @param args The command-line arguments.
     */

    public static void main(String[] args) {
        GameState gameState = new GameState();

        MapController l_mapController = new MapController(gameState, "src/main/resources/canada.map");

        try {
            if (l_mapController.loadMap()) {
                System.out.println("Map is valid.");
                l_mapController.printContinents();
                l_mapController.printCountries();
            } else {
                System.out.println("Map is invalid.");
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public String getFilePath() {
        return d_filePath;
    }

    public void handleShowMapCommand(String[] p_args) {
        try {
            if (p_args.length!=0)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.SHOW_MAP_SYNTAX);
            gameState.showMap();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleValidateMapCommand(String[] p_args) {
        try {
            if (p_args.length!=0)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.VALIDATE_MAP_SYNTAX);
            if (d_gameMapReader.validateMap())
                System.out.println("Map is valid.");
            else
                System.out.println("Map is invalid.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handles the edit map command.
     *
     * @param p_args The command arguments.
     */

    public boolean handleEditMapCommand(String[] p_args) {
        try {
            if (p_args.length!=1) {
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.EDIT_MAP_SYNTAX);
            }

            File f = new File(p_args[0]);
            if (f.exists()) {
                d_filePath = p_args[0];
                gameState.setMapLoaded(loadMap());
            } else {
                String filePath = "src/main/resources/" + p_args[0];
                System.out.println("File does not exist. Creating a new map...");
                createNewMap(filePath);
                gameState.setCurrentFileName(p_args[0]);
                gameState.setMapLoaded(true);
            }


        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Handles the save map command.
     *
     * @param p_args The command arguments.
     */

    public void handleSaveMapCommand(String[] p_args) {
        try {
            if (p_args.length!=1)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.SAVE_MAP_SYNTAX);

            //save the map if the filename in the command is save as the filename in gameState
            // Save the continents and countries to the file
            if (gameState.getCurrentFileName().equals(p_args[0])) {
                saveMap();

            } else {
                System.out.println("Invalid file name. Please use the same file name as the current map.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //save the map according to the gamestate continent and country list

    /**
     * Saves the map to a file.
     */

    public void saveMap() {
        BufferedWriter writer = null;
        try {
            if (gameState.getCurrentFileName().equals("")) {
                throw new Exception("No file name specified.");
            }
            // validate the map before saving
            if (!d_gameMapReader.validateMap()) {
                throw new Exception("Map is invalid.");
            }

            File l_file = new File("src/main/resources/" + gameState.getCurrentFileName());
            if (l_file.exists()) {
                l_file.delete();
            }
            l_file.createNewFile();

            Map<Integer, Continent> l_continents = gameState.getContinents();
            Map<Integer, Country> l_countries = gameState.getCountries();

            writer = new BufferedWriter(new FileWriter(l_file));

            // Save continents
            writer.write("[continents]\n");
            for (Continent continent : l_continents.values()) {
                writer.write(continent.getContinentId() + " " + continent.getContinentValue() + "\n");
            }

            // Save countries
            writer.write("\n[countries]\n");
            int line = 1;
            for (Country country : l_countries.values()) {
                writer.write(line + " " + country.getCountryId() + " " + country.getContinentId() + "\n");
                line++;
            }

            // Assuming you have a method to get borders
            writer.write("\n[borders]\n");
            for (Country country : l_countries.values()) {
                for (Integer neighborId : country.getAdjacentCountries()) {
                    writer.write(country.getCountryId() + " " + neighborId + "\n");
                }
            }

            writer.close(); // Make sure to close the writer to flush and save data
            System.out.println("Map saved to: " + l_file.getAbsolutePath());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (writer!=null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Creates a new map file.
     *
     * @param p_filePath The file path of the new map file.
     */

    public void createNewMap(String p_filePath) {
        try {
            File l_file = new File(p_filePath);
            if (l_file.createNewFile()) {
                System.out.println("File created: " + l_file.getName());

            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Loads the game map file and validates it.
     *
     * @return True if the map is valid, false otherwise.
     * @throws IOException If an I/O error occurs.
     */

    public boolean loadMap() throws IOException {
        d_gameMapReader.parse(d_filePath);
        return d_gameMapReader.validateMap();
    }

    /**
     * Prints the continents and their details.
     */

    public void printContinents() {
        d_gameMapReader.getContinents().forEach((id, continent) -> System.out.println(id + ": " + continent.getContinentName() + " (Bonus: " + continent.getContinentValue() + ", Color: " + continent.getColor() + ")"));
    }

    public boolean handleLoadMapCommand(String[] p_args) {
        try {
            if (p_args.length!=1)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.LOAD_MAP_SYNTAX);

            d_gameMapReader.parse(p_args[0]);
            if (d_gameMapReader.validateMap())
                gameState.setMapLoaded(true);
            else
                throw new Exception("Map is invalid.");

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Prints the countries and their details.
     */

    public void printCountries() {
        d_gameMapReader.getCountries().forEach((id, country) -> {
            System.out.print("CountryID:" + id + " (" + country.getName() + ") is connected to: ");
            country.getAdjacentCountries().forEach(connectedId -> System.out.print(d_gameMapReader.getCountries().get(connectedId).getName() + "->"));
            System.out.println(" (Continent ID: " + country.getContinentId() + "])");
        });
    }


    /**
     * Handles the loadmap command.
     *
     * @param p_fileLocation The file location of the map file.
     */

    public String handleloadMap(String p_fileLocation) {
        try {
            if (p_fileLocation==null || p_fileLocation.isEmpty()) {
                throw new Exception("Invalid file location.");
            }
            File l_file = new File(p_fileLocation);
            if (l_file.exists()) {
                if (d_gameMapReader.parse(p_fileLocation)) {
                    return "Map is valid and loaded.";
                } else {
                    return "Map is invalid.";
                }
            } else {
                throw new Exception("File does not exist.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

//    public static void main(String[] args) {
//        GameState gameState = new GameState();
//
//        MapController l_mapController = new MapController("src/main/resources/canada.map", gameState);
//
//        try {
//            if (l_mapController.loadMap()) {
//                System.out.println("Map is valid.");
//                l_mapController.printContinents();
//                l_mapController.printCountries();
//            } else {
//                System.out.println("Map is invalid.");
//            }
//        } catch (IOException e) {
//            System.err.println("Error reading the file: " + e.getMessage());
//        }
//    }
}
