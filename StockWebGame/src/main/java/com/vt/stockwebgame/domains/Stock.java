package com.vt.stockwebgame.domains;

public class Stock {
    private String symbol;
    private float price;

    public Stock(String ticker, float price) {
        this.symbol = ticker;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public float getPrice() {
        return price;
    }
}