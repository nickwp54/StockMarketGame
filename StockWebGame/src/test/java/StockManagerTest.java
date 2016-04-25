import com.vt.stockwebgame.manager.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StockManagerTest {
    private StockManager stockManager;
    
    @Before
    public void setUp() {
        stockManager = new StockManager();
    }
    
    
    @Test
    public void testGetStockPrice() {
        float price = stockManager.getStockPrice("TSLA");
        System.out.println("Price is: " + price);
        Assert.assertTrue(price > 100 && price < 500);
    }
}
