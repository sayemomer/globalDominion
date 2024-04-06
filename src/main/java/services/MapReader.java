package services;

import java.io.IOException;


/**
 * This is the target interface for the adapter pattern
 * It has one method parse
 * method is used to parse the file
 */

public interface MapReader {

    public boolean parse(String p_filePath) throws IOException;
}
