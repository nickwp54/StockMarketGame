package com.vt.stockwebgame.domains;

import javax.json.*;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StockChart {
    private List<Long> dates;
    private List<Float> values;

    public String getChartJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < dates.size(); i++) {
            sb.append("[");
            sb.append(dates.get(i));
            sb.append(",");
            sb.append(values.get(i));
            sb.append("]");
            if (i < dates.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public StockChart(InputStream jsonStream) {
        dates = new ArrayList<Long>();
        values = new ArrayList<Float>();
        loadAndFormat(jsonStream);
    }

    private void loadAndFormat(InputStream jsonStream) {
        JsonReader reader = Json.createReader(jsonStream);
        JsonObject json = reader.readObject();
        reader.close();
        
        JsonArray jsonDates = json.getJsonArray("Dates");
        JsonArray jsonValues = json.getJsonArray("Elements").getJsonObject(0)
            .getJsonObject("DataSeries").getJsonObject("close")
            .getJsonArray("values");
        
        SimpleDateFormat sdf = 
            new SimpleDateFormat("yyyy-MM-dd'T00:00:00'");
        
        for (int i = 0; i < jsonDates.size(); i++) {
            long timestamp;
            try {
                timestamp = new java.sql.Timestamp(
                        sdf.parse(jsonDates.getString(i)).getTime()).getTime();
                System.out.println(timestamp);
            } catch (ParseException ex) {
                System.out.println(ex.toString());
                continue;
            }
            
            
            dates.add(timestamp);
            values.add(Float.parseFloat(jsonValues.get(i).toString()));
        }
    }
}
