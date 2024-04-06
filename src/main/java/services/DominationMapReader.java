package services;

/**
 * This class is used to read the domination map
 * It has two methods parse and parseContinent
 * parse method is used to parse the file
 * parseContinent method is used to parse the continent
 */

public class DominationMapReader implements AdvanceGameMapReader{

    /**
     * This method is used to parse the file
     * @param p_filePath path of the file
     * @return true if the file is parsed successfully
     */
    @Override
    public boolean parse(String p_filePath) {
        return false;
    }

    /**
     * This method is used to parse the continent
     * @param p_line line to be parsed
     * @param p_continentCount count of the continent
     */

    @Override
    public void parseContinent(String p_line, int p_continentCount) {

    }
}
