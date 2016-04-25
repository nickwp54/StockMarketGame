import com.vt.stockwebgame.domains.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.vt.stockwebgame.domains.Stock;

public class UserTest {
    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername("user");
        user.setPassword("password");
        user.setFirstName("Cadet");
        user.setLastName("Lowspeed");
        user.setEmail("no@no.com");
        user.setPhone("5555558759");
    }

    @Test
    public void testUser() {
        User u = new User();
        assert(u.getBalance() >= 9000 && u.getBalance() <= 11000);
        assert(u.getStockShares() != null);
    }

    @Test
    public void testPurchaseStockPass() {
        Stock stock1 = new Stock("TLL", 108);
        float money = user.getBalance() - (stock1.getPrice() * 10);
        try {
            user.purchaseStock(stock1, 10);
        } catch (Exception e) {
            Assert.fail();
        }
        assert(money == user.getBalance());
        assert(!user.getStockShares().isEmpty());
        assert(user.getStockShares().containsKey("TLL"));
    }

    @Test
    public void testPurchaseStockMore() {
        Stock stock1 = new Stock("TLL", 109);
        float money = user.getBalance() - (stock1.getPrice() * 20);
        try {
            user.purchaseStock(stock1, 10);
            user.purchaseStock(stock1, 10);
        } catch (Exception e) {
            Assert.fail();
        }
        assert(money == user.getBalance());
        assert(!user.getStockShares().isEmpty());
        assert(user.getStockShares().containsKey("TLL"));
        assert(user.getStockShares().get("TLL") == 20);
    }

    @Test
    public void testPurchaseStockFail() {
        Stock stock1 = new Stock("TLL", 109);
        boolean pass = false;
        try {
            user.purchaseStock(stock1, 10000);
        } catch (Exception e) {
            pass = true;
        }
        assert(pass);
    }

    @Test
    public void testSellStockPass() {
        Stock stock1 = new Stock("TLL", 109);
        float money = user.getBalance() - (stock1.getPrice() * 1);
        try {
            user.purchaseStock(stock1, 10);
            user.sellStock(stock1, 9);
        } catch (Exception e) {
            Assert.fail();
        }
        System.out.println(money + "  fadsfasdfdsa  " + user.getBalance());
        assert(money == user.getBalance());
        assert(!user.getStockShares().isEmpty());
        assert(user.getStockShares().containsKey("TLL"));
        assert(user.getStockShares().get("TLL") == 1);
    }

    @Test
    public void testSellStockFail() {
        Stock stock1 = new Stock("TLL", 109);
        boolean pass = false;
        try {
            user.purchaseStock(stock1, 10);
            user.sellStock(stock1, 11);
        } catch (Exception e) {
            pass = true;
        }
        assert(pass);
    }

    @Test
    public void testSellStockAll() {
        Stock stock1 = new Stock("TLL", 109);
        float money = user.getBalance();
        try {
            user.purchaseStock(stock1, 10);
            user.sellStock(stock1, 10);
        } catch (Exception e) {
            Assert.fail();
        }
        assert(money == user.getBalance());
        assert(!user.getStockShares().containsKey("TLL"));
    }



}
