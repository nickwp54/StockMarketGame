import com.vt.stockwebgame.domains.*;
import com.vt.stockwebgame.helpers.*;

import org.junit.Assert;
import org.junit.Test;

public class StockLookupTest {
    @Test
    public void testLoadStock() {
        Stock s = StockLookup.loadStock("AAPL");
        Assert.assertEquals("AAPL", s.getSymbol());
        Assert.assertTrue(s.getPrice() > 0);
    }

    @Test
    public void testLoadFakeStock() {
        Stock s = StockLookup.loadStock("NOTASTOCK");
        Assert.assertNull(s);
    }

    @Test
    public void testLoadStockHistory() {
        StockChart s = StockLookup.loadStockChart("TSLA");
        String data = s.getChartJSON();
        Assert.assertTrue(data.length() > 0);
        Assert.assertTrue(data.startsWith("[\n["));
        Assert.assertNotNull(s);
    }

    @Test
    public void testLoadFakeStockHistory() {
        StockChart s = StockLookup.loadStockChart("BLABLABLA");
        Assert.assertNull(s);
    }
}
