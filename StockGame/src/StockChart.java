import com.oracle.javafx.jmx.json.JSONReader;
import javax.json.Json;

public class StockChart {
    private String json;

    public String getChartJSON() {
        return json;
    }

    public StockChart(String jsonSource) {
        loadAndFormat(jsonSource);
    }

    private void loadAndFormat(String jsonSource) {
//        JSONReader reader = JSONReader.
    }
}
