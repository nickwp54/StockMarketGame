package com.vt.stockwebgame.routes;

import com.vt.stockwebgame.domains.User;
import com.vt.stockwebgame.manager.StockManager;
import org.apache.velocity.tools.generic.NumberTool;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;

public class Router {
    public static void main(String[] args) {
        Spark.staticFileLocation("/static");

        StockManager manager = new StockManager();

        get("/hello", (req, res) -> "Hello World");

        get ("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.html");
        }, new VelocityTemplateEngine());

        get("/viewstock", (request, response) -> {
            return viewStock(manager, request, response);
        }, new VelocityTemplateEngine());
    }

    public static ModelAndView viewStock(StockManager manager, Request request, Response response) {
        User u = new User();
        u.setUsername("testName");

        Map<String, Object> model = new HashMap<>();
        model.put("numberTool", new NumberTool());
        model.put("symbol", request.queryParams("sym").toUpperCase());
        model.put("user", u);

        float price = manager.getStockPrice(request.queryParams("sym"));
        if (price > 0) {
            model.put("valid", true);
            model.put("price", manager.getStockPrice(request.queryParams("sym")));
            model.put("data", manager.getStockData(request.queryParams("sym")));
        }
        else {
            model.put("valid", false);
        }

        return new ModelAndView(model, "viewstock.html");
    }
}
