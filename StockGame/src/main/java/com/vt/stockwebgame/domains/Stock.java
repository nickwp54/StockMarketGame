package com.vt.stockwebgame.domains;

public class Stock {
    private String symbol;
    private float price;
    private int quantity;

    public Stock(String ticker, float price) {
        this.symbol = ticker;
        this.price = price;
    }

    public Stock(String ticker, float price, int quantity) {
        this.symbol = ticker;
        this.price = price;
        this.quantity = quantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public float getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getQuantity() {
        return quantity;
    }
}