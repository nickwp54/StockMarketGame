package com.vt.stockwebgame.routes;

import com.vt.stockwebgame.domains.User;
import com.vt.stockwebgame.manager.StockManager;
import spark.ModelAndView;
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
                System.out.println("fasdf");
            }

            return new ModelAndView(model, "Profile.html");
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
