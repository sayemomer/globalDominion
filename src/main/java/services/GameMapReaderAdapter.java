package services;

import models.GameState;

/**
 * This class is a adapter class for the GameMapReader
 * It has two methods parse and parseContinent
 * parse method is used to parse the file
 * parseContinent method is used to parse the continent
 * This is the adapter class in the adapter pattern
 */

public class GameMapReaderAdapter implements MapReader {

    AdvanceGameMapReader d_advanceGameMapReader;
    private boolean isValidMapType = true;

    /**
     * Constructor for GameMapReaderAdapter
     * @param d_gameState GameState object
     * @param p_mapType type of the map
     */

    public GameMapReaderAdapter(GameState d_gameState, String p_mapType) {
        if (p_mapType.equalsIgnoreCase("Conquest")) {
            d_advanceGameMapReader = new ConquestMapReader(d_gameState);
        } else if (p_mapType.equalsIgnoreCase("Domination")) {
            d_advanceGameMapReader = new DominationMapReader(d_gameState);
        } else {
            System.err.println("Unsupported map type: " + p_mapType);
            CustomPrint.println("Unsupported map type: " + p_mapType);
            isValidMapType = false;
        }
    }

    /**
     * This method is used to parse the file
     * @param p_filePath path of the file
     * @return true if the file is parsed successfully
     */
    @Override
    public boolean parse(String p_filePath) {

        if (!isValidMapType) {
            return false;
        }
        try {
            return d_advanceGameMapReader.parse(p_filePath);
        } catch (Exception e) {
            CustomPrint.println("Error in parsing the file: " + e.getMessage());
            System.err.println("Error in parsing the file: " + e.getMessage());
            return false;
        }
    }
}
