/*
 * Created by Nicholas Phillpott on 2016.04.24  * 
 * Copyright © 2016 Nicholas Phillpott. All rights reserved. * 
 */
package com.vt.stockwebgame.domains;

import com.vt.stockwebgame.helpers.StockLookup;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author painter
 */
public class User implements Comparator<User>, Comparable<User> {
    
    private String username; 
    private String password; 
    private String firstName; 
    private String lastName; 
    private String phone; 
    private String email; 
    private Map<String, Integer> stocks;
    private float balance; 
    
    public User() {
        // 
        stocks = new HashMap<String, Integer>(); 
        balance = new Random().nextFloat() * 1000 + 10000; 
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Integer> getStockShares() {
        return stocks;
    }

    public void setStockShares(Map<String, Integer> stockShares) {
        this.stocks = stockShares;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
    
    // -------------------------------------------------------------------------
    // Added methods
    
    public void purchaseStock(Stock stock, int shares) throws NotEnoughException {
        //Handle the balance change from purchasing, throws expception if
        //not enough money
        if (shares * stock.getPrice() > balance) {
            throw new NotEnoughException(); 
        }
        
        balance = balance - (shares * stock.getPrice());
        
        //Adds the stocks; 
        int current = 0; 
        if (stocks.containsKey(stock.getSymbol())) {
            current = stocks.get(stock.getSymbol());
            stocks.remove(stock.getSymbol());
        }
        
        stocks.put(stock.getSymbol(), shares + current);
    }
    
    public void sellStock(Stock stock, int shares) throws NotEnoughException {
        //If user doesn't have shares
        if (!stocks.containsKey(stock.getSymbol()) || (shares > stocks.get(stock.getSymbol()))) {
            throw new NotEnoughException(); 
        }
        
        balance = balance + (shares * stocks.get(stock.getSymbol()));
        
        int current = stocks.get(stock.getSymbol()) - shares;
        stocks.remove(stock.getSymbol());
        if (current > 0) {
            stocks.put(stock.getSymbol(), current);
        }
    }
    
    public float calculateNetWorth() {
        float total = balance;
        for (Map.Entry<String, Integer> s : stocks.entrySet()) {
            Stock stock = StockLookup.loadStock(s.getKey());
            float price = stock != null ? stock.getPrice() : 0;
            total += price * s.getValue();
        }
        return total;
    }

    @Override
    public int compare(User u, User u2) {
        return Float.compare(u.calculateNetWorth(), u2.calculateNetWorth());
    }
    
    @Override
    public int compareTo(User u){
        return ((Float)this.calculateNetWorth()).compareTo(u.calculateNetWorth());
    }
}
