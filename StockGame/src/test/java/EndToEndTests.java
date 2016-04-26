import com.vt.stockwebgame.domains.User;
import com.vt.stockwebgame.helpers.StockLookup;
import com.vt.stockwebgame.manager.StockManager;
import com.vt.stockwebgame.routes.Router;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.vt.stockwebgame.domains.User;

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

    /**
     * End to end case representing a user signing up for the site
     *
     * @throws Exception
     */
    @Test
    public void signUpTest() throws Exception {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        user.setFirstName("Nich");
        user.setLastName("Phillpott");
        user.setEmail("nick@p.com");
        user.setPhone("6665553739");

        manager.signUp(user);

        // This means that the user has been added to list of users
        assert(!manager.getActiveUsers().isEmpty());

    }

    /**
     * End to end case representing a user logging in and logging out
     *
     * @throws Exception
     */
    @Test
    public void loginLogoutTest() throws Exception {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        user.setFirstName("Nich");
        user.setLastName("Phillpott");
        user.setEmail("nick@p.com");
        user.setPhone("6665553739");

        manager.signUp(user);

        manager.loginUser("user", "password");

        manager.logout();

        Assert.assertNull(manager.findActiveUser("user"));

    }

    /**
     * End to end case representing a user logging in and buying a stock.
     *
     * @throws Exception
     */
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

    /**
     * End to end case representing a user logging in and selling a stock.
     *
     * @throws Exception
     */
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