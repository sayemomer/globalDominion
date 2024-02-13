//package controllers;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * The MapControllerTest class is responsible for testing the MapController class.
// */
//
//class MapControllerTest {
//
//    private MapController l_mapController;
//
//    /**
//     * Sets up the context for the test.
//     */
//
//
//    @BeforeEach
//    void setUp() {
//        l_mapController = new MapController("src/test/resources/canada.map", dFilePath);
//    }
//
//    /**
//     * Tests the loadMap method.
//     */
//    @Test
//    void testLoadMap() {
//        // Test loading the map and validating it
//        assertDoesNotThrow(() -> {
//            boolean result = l_mapController.loadMap();
//            assertTrue(result, "The map should load and validate successfully");
//        });
//    }
//
//    /**
//     * Tests the printContinents method.
//     */
//    @Test
//    void testPrintContinents() {
//        // Test printing the continents
//        assertDoesNotThrow(() -> {
//            l_mapController.loadMap();
//            l_mapController.printContinents();
//        });
//    }
//
//    /**
//     * Tests the printCountries method.
//     */
//    @Test
//    void testPrintCountries() {
//        // Test printing the countries
//        assertDoesNotThrow(() -> {
//            l_mapController.loadMap();
//            l_mapController.printCountries();
//        });
//    }
//
//    /**
//     * Tests the main method.
//     */
//    @Test
//    void testMain() {
//        // Test the main method
//        assertDoesNotThrow(() -> {
//            MapController.main(new String[] {});
//        });
//    }
//
//}
