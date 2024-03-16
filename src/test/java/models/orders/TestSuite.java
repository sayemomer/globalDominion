package models.orders;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        AirliftOrderTest.class,
        BlockadeOrderTest.class,
        BombOrderTest.class
})
//@SelectPackages("com.yourpackage.tests") // Alternatively, select packages
public class TestSuite {
    // This class remains empty, it's used only as a holder for the above annotations
}
