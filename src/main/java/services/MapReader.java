package services;

import java.io.IOException;


/**
 * This is the target interface for the adapter pattern
 * It has one method parse
 * method is used to parse the file
 */

public interface MapReader {

    /**
     * This method is used to parse the file
     * @param p_filePath path of the file
     * @return boolean value
     * @throws IOException
     */

    public boolean parse(String p_filePath) throws IOException;
}
