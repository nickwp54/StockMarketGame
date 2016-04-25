import com.vt.stockwebgame.domains.User;
import com.vt.stockwebgame.manager.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StockManagerTest {
    private StockManager stockManager;
    private User user;
    
    @Before
    public void setUp() {
        stockManager = new StockManager();
        user = new User();
        user.setUsername("user");
        user.setPassword("password");
        user.setFirstName("Cadet");
        user.setLastName("Lowspeed");
        user.setEmail("no@no.com");
        user.setPhone("5555558759");
    }
    
    
    @Test
    public void testGetStockPrice() {
        float price = stockManager.getStockPrice("TSLA");
        Assert.assertTrue(price > 0 && price < 1000);
    }
    
    @Test
    public void testGetStockData() {
        String data = stockManager.getStockData("TSLA");
        Assert.assertNotNull(data);
        Assert.assertTrue(data.length() > 0);
    }

    @Test
    public void testLoginUser() {
        stockManager.signUp(user);
        stockManager.loginUser("user", "password");
        assert(stockManager.getUser().equals(user));
    }

    @Test
    public void testLogout() {
        stockManager.signUp(user);
        stockManager.loginUser("user", "password");
        stockManager.logout();
        assert(stockManager.getUser() == null);

    }

    @Test
    public void testSignUp() {
        stockManager.signUp(user);
        assert(stockManager.getActiveUsers().contains(user));
    }

}
