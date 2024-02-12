package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The GameMapReaderTest class is responsible for testing the GameMapReader class.
 */

public class GameMapReaderTest {

        private GameMapReader l_gameMapReader;

        /**
        * Sets up the context for the test.
        */

        @BeforeEach
        void setUp() {
            l_gameMapReader = new GameMapReader();
        }

        /**
        * Tests the parse method.
        */
        @Test
        void testParse() {
            // Test parsing a valid map
            assertDoesNotThrow(() -> l_gameMapReader.parse("src/test/resources/canada.map"));
        }

        /**
        * Tests the validateMap method.
        */
        @Test
        void testValidateMap() {
            // Test validating a valid map
            assertDoesNotThrow(() -> {
                l_gameMapReader.parse("src/test/resources/canada.map");
                boolean result = l_gameMapReader.validateMap();
                assertTrue(result, "The map should be valid");
            });
        }

        /**
        * Tests the getContinents method.
        */
        @Test
        void testGetContinents() {
            // Test getting the continents
            assertDoesNotThrow(() -> {
                l_gameMapReader.parse("src/test/resources/canada.map");
                assertNotNull(l_gameMapReader.getContinents(), "The continents should not be null");
            });
        }

        /**
        * Tests the getCountries method.
        */
        @Test
        void testGetCountries() {
            // Test getting the countries
            assertDoesNotThrow(() -> {
                l_gameMapReader.parse("src/test/resources/canada.map");
                assertNotNull(l_gameMapReader.getCountries(), "The countries should not be null");
            });
        }
}
