import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


public class StockLookup {
    static final String API_CALL = "http://dev.markitondemand.com/MODApis/Api/v2/Quote/xml?symbol=%1$2s";

    public static Stock loadStock(String ticker) {
        try {
            String address = String.format(API_CALL, ticker);
            URL url = new URL(address);
            URLConnection conn = url.openConnection();
            Stock stock = deserializeXMLToStock(conn.getInputStream());
            return stock;
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