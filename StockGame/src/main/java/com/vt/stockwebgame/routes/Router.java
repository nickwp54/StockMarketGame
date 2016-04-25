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
import static spark.Spark.post;

public class Router {
    public static void main(String[] args) {
        Spark.staticFileLocation("/static");

        StockManager manager = new StockManager();

        get("/hello", (req, res) -> "Hello World");

        get ("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.html");
        }, new VelocityTemplateEngine());

        get ("/signup", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "SignUp.html");
        }, new VelocityTemplateEngine());

        post("/signup", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            User user = new User();
            try {
                user.setUsername(request.queryParams("username"));
                user.setPassword(request.queryParams("password"));
                user.setFirstName(request.queryParams("firstName"));
                user.setLastName(request.queryParams("lastName"));
                user.setPhone(request.queryParams("phone"));
                user.setEmail(request.queryParams("email"));

                if (!manager.signUp(user)) {
                    model.put("error", "Username Taken");
                    return new ModelAndView(model, "signup.html");
                }
            } catch (Exception e) {}
            System.out.println(user);
            return new ModelAndView(model, "index.html");
        }, new VelocityTemplateEngine());


        post("/login", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            try {
                if(manager.loginUser(request.queryParams("username"), request.queryParams("password"))) {
                    request.session().attribute("user", request.queryParams("username"));
                }
                else {
                    model.put("error", "Username or Password Incorrect");
                    return new ModelAndView(model, "index.html");
                }
            } catch (Exception e) {
            }
            model.put("user", currentUser(manager, request));
            return new ModelAndView(model, "Profile.html");
        }, new VelocityTemplateEngine());

        get("/profile", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("user", currentUser(manager, request));
            return new ModelAndView(model, "Profile.html");
        }, new VelocityTemplateEngine());

        get("/viewstock", (request, response) -> {
            return viewStock(manager, request, response);
        }, new VelocityTemplateEngine());
    }

    private static User currentUser(StockManager manager, Request request) {
        return manager.findActiveUser(request.session().attribute("user"));
    }

    public static ModelAndView viewStock(StockManager manager, Request request, Response response) {
        User u = currentUser(manager, request);

        String symbol = request.queryParams("sym");
        Map<String, Object> model = new HashMap<>();
        model.put("numberTool", new NumberTool());
        model.put("user", u);

        if (symbol != null && symbol.length() > 0) {
            model.put("symbol", symbol.toUpperCase());
            float price = manager.getStockPrice(request.queryParams("sym"));
            if (price > 0) {
                model.put("valid", true);
                model.put("price", manager.getStockPrice(request.queryParams("sym")));
                model.put("data", manager.getStockData(request.queryParams("sym")));
            }
            else {
                model.put("valid", false);
            }
        }
        else {
            model.put("valid", false);
        }

        return new ModelAndView(model, "viewstock.html");
    }
}
