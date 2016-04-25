package com.vt.stockwebgame.routes;

import com.vt.stockwebgame.domains.NotEnoughException;
import com.vt.stockwebgame.domains.Stock;
import com.vt.stockwebgame.domains.User;
import com.vt.stockwebgame.helpers.StockLookup;
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
        setupTestUser(manager);

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

        get("/buystock", (request, response) -> {
            return buyStock(manager, request);
        }, new VelocityTemplateEngine());

        get("/sellstock", (request, response) -> {
            return sellStock(manager, request);
        }, new VelocityTemplateEngine());

        post("/login", (request, response) -> {
            try {
                if(manager.loginUser(request.queryParams("username"), request.queryParams("password"))) {
                    request.session().attribute("user", request.queryParams("username"));
                }
                else {
                    Map<String, Object> model = new HashMap<>();
                    model.put("error", "Username or Password Incorrect");
                    return new ModelAndView(model, "index.html");
                }
            } catch (Exception e) {

            }
            Map<String, Object> model = createUserModel(manager, request);
            return new ModelAndView(model, "Profile.html");
        }, new VelocityTemplateEngine());

        get("/profile", (request, response) -> {
            Map<String, Object> model = createUserModel(manager, request);
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

        String symbol = request.queryParams("symbol");
        Map<String, Object> model = createModelForUser(u);

        if (symbol != null && symbol.length() > 0) {
            model.put("symbol", symbol.toUpperCase());
            float price = manager.getStockPrice(symbol);
            if (price > 0) {
                model.put("valid", true);
                model.put("price", manager.getStockPrice(symbol));
                model.put("data", manager.getStockData(symbol));
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

    public static ModelAndView buyStock(StockManager manager, Request request) {
        Map<String, Object> model = createUserModel(manager, request);
        User u = (User) model.get("user");
        String symbol = request.queryParams("symbol");
        Stock stock = null;

        int quantity = 0;
        try {
            stock = StockLookup.loadStock(symbol);
            quantity = Integer.parseInt(request.queryParams("quantity"));
            u.purchaseStock(StockLookup.loadStock(symbol), quantity);
            model.put("buy_price", quantity * stock.getPrice());
            model.put("buy_success", true);
            model.put("symbol", symbol);
            model.put("quantity", quantity);
        }
        catch (NotEnoughException notEnough) {
            model.put("buy_error", true);
            model.put("buy_price", quantity * stock.getPrice());
            model.put("symbol", symbol);
            model.put("quantity", quantity);
        }
        catch (Exception e) {
            model.put("buy_error", true);
        }

        return new ModelAndView(model, "Profile.html");
    }

    public static ModelAndView sellStock(StockManager manager, Request request) {
        Map<String, Object> model = createUserModel(manager, request);
        User u = (User) model.get("user");
        String symbol = request.queryParams("symbol");
        Stock stock = null;

        int quantity = 0;
        try {
            stock = StockLookup.loadStock(symbol);
            quantity = Integer.parseInt(request.queryParams("quantity"));
            u.sellStock(StockLookup.loadStock(symbol), quantity);
            model.put("sell_price", quantity * stock.getPrice());
            model.put("sell_success", true);
            model.put("symbol", symbol);
            model.put("quantity", quantity);
        }
        catch (NotEnoughException notEnough) {
            model.put("sell_error", true);
            model.put("sell_price", quantity * stock.getPrice());
            model.put("symbol", symbol);
            model.put("quantity", quantity);
        }
        catch (Exception e) {
            model.put("sell_error", true);
        }

        return new ModelAndView(model, "Profile.html");
    }

    public static Map<String, Object> createUserModel(StockManager manager, Request request) {
        return createModelForUser(currentUser(manager, request));
    }

    public static Map<String, Object> createModelForUser(User u) {
        Map<String, Object> map = new HashMap<>();
        map.put("numberTool", new NumberTool());
        map.put("user", u);
        return map;
    }

    public static void setupTestUser(StockManager manager) {
        User user = new User();
        try {
            user.setUsername("mfav");
            user.setPassword("123");
            user.setFirstName("Matt");
            user.setLastName("Favero");
            user.setPhone("7044916987");
            user.setEmail("mafavero@vt.edu");
        } catch (Exception e) {
            System.out.println("Error creating test user");
            return;
        }
        manager.signUp(user);
    }
}
