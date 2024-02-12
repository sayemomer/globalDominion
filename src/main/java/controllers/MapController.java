package controllers;

import models.GameMap;
import models.GameState;

import services.GameMapReader;

import java.io.File;
import java.io.IOException;

/**
 * The MapController class is responsible for handling the map parsing,
 * validation, and display of continents and countries from a game map file.
 * It utilizes the GameMapReader service to read and validate the map file.
 */

public class MapController {

    private final GameMapReader d_gameMapReader;
    private final String d_filePath;
    GameState gameState;

    /**
     * Constructor for the MapController class.
     *
     * @param p_filePath The file path of the game map file.
     */

    public MapController(String p_filePath, GameState p_gameState) {
        this.d_gameMapReader = new GameMapReader();
        this.d_filePath = p_filePath;
        gameState = p_gameState;
    }

    /**
     * Main method to test the MapController class.
     *
     * @param args The command-line arguments.
     */

    public static void main(String[] args) {
        GameState gameState = new GameState();

        MapController l_mapController = new MapController("src/main/resources/canada.map", gameState);

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


    public void handleShowMap(String[] p_args) {
        try {
            if (p_args.length != 0)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.SHOW_MAP_SYNTAX);
            gameState.getGameMap().showMap();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleValidateMap(String[] p_args) {
        try {
            if (p_args.length != 0)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.VALIDATE_MAP_SYNTAX);
            gameState.getGameMap().validateMap();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleEditMap(String[] p_args) throws Exception {
        try {
            if (p_args.length != 1){
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.EDIT_MAP_SYNTAX);
            }
                
            File f = new File(p_args[0]);
            if (f.exists()) {
                gameState.setMap(new GameMap(p_args[0]));
            } else {
                gameState.setMap(new GameMap());
            }

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
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
        d_gameMapReader.getContinents().forEach((id, continent) ->
        System.out.println(id + ": " + continent.getContinentName() + " (Bonus: " + continent.getContinentValue() + ", Color: " + continent.getColor() + ")"));
    }

    public void handleLoadMap(String[] p_args) {
        try {
            if (p_args.length != 1)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.LOAD_MAP_SYNTAX);
            File f = new File(p_args[0]);
            if (f.exists()) {
                gameState.setMap(new GameMap(p_args[0]));
            } else {
                throw new Exception("Map file does not exist.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleSaveMap(String[] p_args) {
        try {
            if (p_args.length != 1)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.SAVE_MAP_SYNTAX);
            gameState.getGameMap().saveMap();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints the countries and their details.
     */

    public void printCountries() {
        d_gameMapReader.getCountries().forEach((id, country) -> {
            System.out.print("CountryID:" + id + " (" + country.getCountryName() + ") is connected to: ");
            country.getAdjacentCountries().forEach(connectedId -> System.out.print(d_gameMapReader.getCountries().get(connectedId).getCountryName() + "->"));
            System.out.println(" (Continent ID: " + country.getContinentId() + "])");
        });
    }
}
