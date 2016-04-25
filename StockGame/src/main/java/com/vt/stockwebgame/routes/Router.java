package com.vt.stockwebgame.routes;

import static spark.Spark.get;

/**
 * Created by mfav on 4/25/16.
 */
public class Router {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }
}
