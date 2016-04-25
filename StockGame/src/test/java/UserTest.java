import com.vt.stockwebgame.domains.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

    }

    @Test
    public void testPurchaseStock() {

    }

    @Test
    public void testSellStock() {
    }

    @Test
    public void testCalculateNetWorth() {

    }

}
