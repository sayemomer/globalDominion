package services;

import models.GameState;

/**
 * This class is a adapter class for the GameMapReader
 * It has two methods parse and parseContinent
 * parse method is used to parse the file
 * parseContinent method is used to parse the continent
 */

public class GameMapReaderAdapter implements MapReader {

    AdvanceGameMapReader d_advanceGameMapReader;

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
        }
    }

    /**
     * This method is used to parse the file
     * @param p_filePath path of the file
     * @return true if the file is parsed successfully
     */
    @Override
    public boolean parse(String p_filePath) {
        try {
            return d_advanceGameMapReader.parse(p_filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
