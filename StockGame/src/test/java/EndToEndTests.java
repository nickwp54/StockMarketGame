import com.vt.stockwebgame.domains.User;
import com.vt.stockwebgame.helpers.StockLookup;
import com.vt.stockwebgame.manager.StockManager;
import com.vt.stockwebgame.routes.Router;
import org.junit.Assert;
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
        manager = new StockManager();
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
        // Log-in
        manager.loginUser("mfav", "123");
        User u = manager.findActiveUser("mfav");

        // Purchase stock
        u.purchaseStock(StockLookup.loadStock("NFLX"), 5);

        // Ensure stock is in account
        Assert.assertEquals(5, (int) u.getStockShares().get("NFLX"));
    }

    @Test
    public void loginSellStockTest() throws Exception {
        // Log-in
        manager.loginUser("mfav", "123");
        User u = manager.findActiveUser("mfav");

        // Purchase stock
        u.purchaseStock(StockLookup.loadStock("NFLX"), 5);

        // Sell stock
        u.sellStock(StockLookup.loadStock("NFLX"), 5);

        // Ensure stock is not account
        Assert.assertFalse(u.getStockShares().containsKey("NFLX"));
    }
}