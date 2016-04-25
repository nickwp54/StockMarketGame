import com.vt.stockwebgame.manager.StockManager;
import com.vt.stockwebgame.routes.Router;
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
        Router.setupTestUser(manager);
    }

    @Test
    public void signUpTest() throws Exception {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        user.setFirstName("Nich");
        user.setLastName("Phillpott");
        user.setEmail("nick@p.com");
        user.setPhone("6665553739");

        
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