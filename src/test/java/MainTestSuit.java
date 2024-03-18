
import org.junit.platform.suite.api.Suite;

import org.junit.platform.suite.api.SelectPackages;

@SelectPackages({"models.orders", "controllers","phases","services"})
@Suite
public class MainTestSuit {
}
