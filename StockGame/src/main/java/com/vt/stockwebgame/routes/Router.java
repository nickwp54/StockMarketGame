package com.vt.stockwebgame.routes;

import com.vt.stockwebgame.manager.StockManager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;

public class Router {
    public static void main(String[] args) {
        StockManager manager = new StockManager();

        get("/hello", (req, res) -> "Hello World");

        get ("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.html");
        }, new VelocityTemplateEngine());

        get("/viewstock", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("symbol", request.queryParams("sym"));
            model.put("price", manager.getStockPrice(request.queryParams("sym")));
            model.put("data", manager.getStockData(request.queryParams("sym")));

            return new ModelAndView(model, "ViewStock.html");
        }, new VelocityTemplateEngine());
    }
}
