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
        Assert.assertTrue(price > 0 && price < 1000);
    }
    
    @Test
    public void testGetStockData() {
        String data = stockManager.getStockData("TSLA");
        Assert.assertNotNull(data);
        Assert.assertTrue(data.length() > 0);
    }
}
