package models.orders;

import models.orders.AirliftTest;
import models.orders.BlockadeTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        AirliftTest.class,
        BlockadeTest.class,
        BombTest.class
})
//@SelectPackages("com.yourpackage.tests") // Alternatively, select packages
public class TestSuite {
    // This class remains empty, it's used only as a holder for the above annotations
}
