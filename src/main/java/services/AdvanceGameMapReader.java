package services;

/**
 * This is the adaptee interface for the adapter pattern
 * It has two methods parse and parseContinent
 * parse method is used to parse the file
 * parseContinent method is used to parse the continent
 */

public interface AdvanceGameMapReader {
    public boolean parse(String p_filePath) throws Exception;
    public void parseContinent(String p_line, int p_continentCount);
}
