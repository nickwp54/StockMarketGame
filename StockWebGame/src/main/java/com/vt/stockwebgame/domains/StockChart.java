package com.vt.stockwebgame.domains;

import java.io.InputStream;
import javax.json.*;

public class StockChart {
    private String json;

    public String getChartJSON() {
        return json;
    }

    public StockChart(InputStream jsonStream) {
        loadAndFormat(jsonStream);
    }

    private void loadAndFormat(InputStream jsonStream) {
        JsonReader reader = Json.createReader(jsonStream);
        JsonObject json = reader.readObject();
        reader.close();
        
        JsonArray dates = json.getJsonArray("Dates");
        JsonArray values = json.getJsonArray("Elements").getJsonObject(0).
                getJsonObject("DataSeries").getJsonObject("close")
                .getJsonArray("values");
        
        for (int i = 0; i < dates.size(); i++) {
            System.out.println(dates.getString(i));
            System.out.println(values.get(i));
        }
    }
}
