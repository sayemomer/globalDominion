package services;

/**
 * This is the adaptee interface for the adapter pattern
 * It has two methods parse and parseContinent
 * parse method is used to parse the file
 * parseContinent method is used to parse the continent
 */

public interface AdvanceGameMapReader {

    /**
     * This method is used to parse the file
     * @param p_filePath path of the file
     * @return boolean value
     * @throws Exception if there is an error in reading the file
     */
    public boolean parse(String p_filePath) throws Exception;

    /**
     * This method is used to parse the continent
     * @param p_line line to be parsed
     * @param p_continentCount count of the continent
     */
    public void parseContinent(String p_line, int p_continentCount);
}
