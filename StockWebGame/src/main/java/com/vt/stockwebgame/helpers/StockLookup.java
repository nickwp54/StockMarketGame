/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vt.stockwebgame.helpers;

import com.vt.stockwebgame.domains.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class StockLookup {
    static final String PRICE_QUERY = "http://dev.markitondemand.com/MODApis/Api/v2/Quote/xml?symbol=%1$2s";
    static final String CHART_QUERY = "http://dev.markitondemand.com/MODApis/Api/v2/InteractiveChart/json?parameters=";
    static final String CHART_PARAMS = "{\"Normalized\":false,\"NumberOfDays\":365,\"DataPeriod\":\"Day\","
        +"\"Elements\":[{\"Symbol\":\"%1$2s\",\"Type\":\"price\",\"Params\":[\"c\"]}]}";

    static final String ENCODING = "UTF-8";

    public static Stock loadStock(String ticker) {
        try {
            String address = String.format(PRICE_QUERY, ticker);
            URL url = new URL(address);
            URLConnection conn = url.openConnection();
            return deserializeXMLToStock(conn.getInputStream());
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public static StockChart loadStockChart(String ticker) {
        try {
            String substituted = String.format(CHART_PARAMS, ticker);
            String encoded = URLEncoder.encode(substituted, ENCODING);
            String address = CHART_QUERY + encoded;
            URL url = new URL(address);
            URLConnection conn = url.openConnection();
            return new StockChart(conn.getInputStream());
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    private static Stock deserializeXMLToStock(InputStream xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xml);
            doc.getDocumentElement().normalize();

            String symbol = doc.getElementsByTagName("Symbol").item(0).getTextContent();
            float price = Float.parseFloat(
                    doc.getElementsByTagName("LastPrice").item(0).getTextContent());

            return new Stock(symbol, price);
        }
        catch (Exception e) {
            return null;
        }
    }
}
