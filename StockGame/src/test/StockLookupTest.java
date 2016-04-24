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
}
