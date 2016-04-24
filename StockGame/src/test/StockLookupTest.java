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
        String s = StockLookup.loadStockChart("TSLA");
        Assert.assertTrue(s.contains("Positions"));
        Assert.assertTrue(s.contains("Dates"));
        Assert.assertTrue(s.contains("TSLA"));
    }

    @Test
    public void testLoadFakeStockHistory() {
        String s = StockLookup.loadStockChart("BLABLABLA");
        Assert.assertNull(s);
    }
}
