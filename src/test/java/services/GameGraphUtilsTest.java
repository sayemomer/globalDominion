package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The GameGraphUtilsTest class is responsible for testing the GameGraphUtils class.
 */

public class GameGraphUtilsTest {

    private GameGraphUtils l_gameGraphUtils;

    /**
     * Sets up the context for the test.
     */

    @BeforeEach
    void setUp() {
        l_gameGraphUtils = new GameGraphUtils();
    }

    /**
     * Tests the isGraphConnected method.
     */
    @Test
    void testIsGraphConnected() {
        // Test as a empty graph
        assertFalse(GameGraphUtils.isGraphConnected(null), "The graph should be disconnected");
    }

    /**
     * Tests the isContinentConnected method.
     */
    @Test
    void testIsContinentConnected() {
        // Test as a empty graph
        assertFalse(GameGraphUtils.isContinentConnected(null, 1), "The continent should be disconnected");
    }

    /**
     * test the dfs method
     */
    @Test
    void testDfs() {
        // Test as a empty graph
        assertFalse(GameGraphUtils.dfs(1, null, null), "The graph should be disconnected");
    }
}
