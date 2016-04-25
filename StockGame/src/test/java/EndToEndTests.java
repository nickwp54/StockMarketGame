import com.vt.stockwebgame.manager.StockManager;
import com.vt.stockwebgame.routes.Router;
import org.junit.Before;
import org.junit.Test;
import spark.Request;

import static org.junit.Assert.*;

/**
 * End to end tests!
 */
public class EndToEndTests {
    private StockManager manager;

    @Before
    public void setup() {
        Router.setupTestUser(manager);
    }

    @Test
    public void signUpTest() throws Exception {

    }

    @Test
    public void loginToAccountTest() throws Exception {

    }

    @Test
    public void loginPurchaseStockTest() throws Exception {

    }

    @Test
    public void loginLogoutTest() throws Exception {

    }
}