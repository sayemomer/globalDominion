package controllers;

import models.Continent;
import models.Country;
import models.GameState;
import services.CustomPrint;
import services.GameMapReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * The MapController class is responsible for handling the map parsing,
 * validation, and display of continents and countries from a game map file.
 * It utilizes the GameMapReader service to read and validate the map file.
 */

public class MapController {

    private final GameMapReader d_gameMapReader;
    GameState d_gameState;
    private String d_filePath;

    /**
     * Constructor for the MapController class.
     *
     * @param gameState  The game state.
     * @param d_filePath The file path of the map file.
     */
    public MapController(GameState gameState, String d_filePath) {
        this.d_gameMapReader = new GameMapReader(gameState);
        this.d_filePath = d_filePath;
        this.d_gameState = gameState;
    }

    /**
     * Constructor for the MapController class.
     *
     * @param gameState The game state.
     */
    public MapController(GameState gameState) {
        this.d_gameMapReader = new GameMapReader(gameState);
        this.d_filePath = "";
        this.d_gameState = gameState;
    }

    /**
     * Constructor for the MapController class.
     *
     * @param d_filePath The file path of the map file.
     */
    public MapController(String d_filePath) {
        this.d_gameState = new GameState();
        this.d_gameMapReader = new GameMapReader(d_gameState);
        this.d_filePath = d_filePath;
    }


    /**
     * gets the file path of the map file.
     * @return the file path of the map file.
     */
    public String getFilePath() {
        return d_filePath;
    }

    /**
     * Handles the validate command.
     *
     * @param p_args The command arguments.
     */
    public void handleValidateMapCommand(String[] p_args) {
        try {
            if (p_args.length != 0)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.VALIDATE_MAP_SYNTAX);
            if (d_gameMapReader.validateMap())
                CustomPrint.println("Map is valid.");
            else {
                d_gameState.removeAction(GameState.GameAction.VALID_MAP_LOADED);
                throw new Exception("Map is invalid.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Handles the edit map command.
     * creates a new map if the file does not exist
     *
     * @param p_args The command arguments.
     * @return True if the map is valid, false otherwise.
     */

    public boolean handleEditMapCommand(String[] p_args) {
        try {
            if (p_args.length != 1) {
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.EDIT_MAP_SYNTAX);
            }

            String l_fileLocation = "src/main/resources/" + p_args[0];

            File l_file = new File(l_fileLocation);
            if (l_file.exists()) {
                d_filePath = p_args[0];
                d_gameState.setMapLoaded(loadMap());
            } else {
                CustomPrint.println("File does not exist. Creating a new map...");
                createNewMap(l_fileLocation);
                d_gameState.setCurrentFileName(p_args[0]);
                d_gameState.setMapLoaded(true);
            }
            
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        return true;
    }


    /**
     * Handles the save map command.
     *
     * @param p_args The command arguments.
     * @return True if the map is valid, false otherwise.
     */
    public boolean handleSaveMapCommand(String[] p_args) {
        try {
            if (p_args.length != 1)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.SAVE_MAP_SYNTAX);

            // save the map if the filename in the command is same as the filename in gameState
            // Save the continents and countries to the file

            if (d_gameState.getCurrentFileName().equals(p_args[0])) {
                CustomPrint.println("saving map ...");
                if (d_gameMapReader.validateMap()) {
                    saveMap();
                    d_gameState.setActionDone(GameState.GameAction.VALID_MAP_LOADED);
                    return true;
                } else {
                    d_gameState.removeAction(GameState.GameAction.VALID_MAP_LOADED);
                    throw new Exception("Map is invalid.");
                }
            } else {
                System.err.println("Invalid file name. Please use the same file name as the current map.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Saves the map to a file.
     * checks the map format from gameState and saves the map accordingly
     */
    public void saveMap() {

        // check for the map format from gameState

        try {
            String mapFormat = d_gameState.getMapFormat();

            if(mapFormat.equals("domination")) {
                saveDominationMap();
            } else if(mapFormat.equals("conquest")) {
                saveConquestMap();
            } else {
                System.err.println("Invalid map format.");
            }

        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Saves the map to a file in conquest format.
     */

    private void saveConquestMap() {

        try {

            System.out.println("Saving the map to conquest format...");

            File l_file = new File("src/main/resources/" + d_gameState.getCurrentFileName());
            FileWriter l_fileWriter = new FileWriter(l_file);
            BufferedWriter l_bufferedWriter = new BufferedWriter(l_fileWriter);


            //Write the map format
            l_bufferedWriter.write("[Map]\n");
            l_bufferedWriter.write("author=globalDominion\n");
            l_bufferedWriter.newLine();


            // Write the continents
            l_bufferedWriter.write("[Continents]\n");
            for (Map.Entry<Integer, Continent> l_continent : d_gameState.getContinents().entrySet()) {
                l_bufferedWriter.write(l_continent.getValue().getContinentName() + "=" + l_continent.getValue().getContinentValue());
                l_bufferedWriter.newLine();
            }

            // Write the countries
            l_bufferedWriter.write("\n[Territories]\n");
            for (Map.Entry<Integer, Country> l_country : d_gameState.getCountries().entrySet()) {
                // write as a country name,continent name, neighbor1, neighbor2, ... format

                //get the adjacent countries  names as a string
                StringBuilder l_adjacentCountries = new StringBuilder();

                for (Integer l_adjacentCountryId : l_country.getValue().getAdjacentCountries()) {
                    l_adjacentCountries.append(
                            d_gameState.getCountries().get(l_adjacentCountryId).getName()).append(",");
                };

                // remove the last comma
                if (l_adjacentCountries.length() > 0) {
                    l_adjacentCountries.deleteCharAt(l_adjacentCountries.length() - 1);
                }

                l_bufferedWriter.write(l_country.getValue().getName() + "," + "0" + "," +"0"+"," + l_country.getValue().getContinentId() + "," + l_adjacentCountries);
                l_bufferedWriter.newLine();
            }

            l_bufferedWriter.close();
            l_fileWriter.close();
            CustomPrint.println("Map saved successfully.");

        } catch (IOException e) {
            System.err.println("An error occurred.");
            e.printStackTrace();

        }
    }

    /**
     * Saves the map to a file in domination format.
     */

    private void saveDominationMap(){

        BufferedWriter l_writer = null;
        try {
            if (d_gameState.getCurrentFileName().equals("")) {
                throw new Exception("No file name specified.");
            }
            // validate the map before saving
            if (!d_gameMapReader.validateMap()) {

                throw new Exception("Map is invalid.");
            }

            File l_file = new File("src/main/resources/" + d_gameState.getCurrentFileName());
            if (l_file.exists()) {
                l_file.delete();
            }
            l_file.createNewFile();

            Map<Integer, Continent> l_continents = d_gameState.getContinents();
            Map<Integer, Country> l_countries = d_gameState.getCountries();

            l_writer = new BufferedWriter(new FileWriter(l_file));

            // Save continents
            l_writer.write("[continents]\n");
            for (Continent continent : l_continents.values()) {
                l_writer.write(continent.getContinentId() + " " + continent.getContinentValue() + "\n");
            }

            // Save countries
            l_writer.write("\n[countries]\n");
            int line = 1;
            for (Country country : l_countries.values()) {
                l_writer.write(line + " " + country.getCountryId() + " " + country.getContinentId() + "\n");
                line++;
            }

            // Assuming you have a method to get borders
            l_writer.write("\n[borders]\n");
            for (Country country : l_countries.values()) {
                for (Integer neighborId : country.getAdjacentCountries()) {
                    l_writer.write(country.getCountryId() + " " + neighborId + "\n");
                }
            }

            l_writer.close(); // Make sure to close the writer to flush and save data
            CustomPrint.println("Map saved to: " + l_file.getAbsolutePath());

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (l_writer != null) {
                try {
                    l_writer.close();
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
                CustomPrint.println("File created: " + l_file.getName());

            } else {
                System.err.println("File already exists.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred.");
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
     * Handles the load map command.
     *
     * @param p_args The command arguments.
     * @return True if the map is valid, false otherwise.
     */
    public boolean handleloadMapCommand(String[] p_args) {
        try {
            if (p_args.length != 1)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.LOAD_MAP_SYNTAX);

            String l_fileLocation = "src/main/resources/" + p_args[0];
            File l_file = new File(l_fileLocation);
            if (!l_file.exists()) {
                throw new Exception("File does not exist.");
            }

            if (d_gameMapReader.parse(l_fileLocation)) {
                CustomPrint.println("Map is valid and loaded.");
                d_gameState.setActionDone(GameState.GameAction.VALID_MAP_LOADED);
                d_gameState.setCurrentFileName(p_args[0]);
            } else {
                throw new Exception("Map is invalid.");
            }

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }


}
