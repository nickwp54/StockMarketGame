/*
 * Created by Nicholas Phillpott on 2016.04.24  * 
 * Copyright © 2016 Nicholas Phillpott. All rights reserved. * 
 */
package com.vt.stockwebgame.manager;

import com.vt.stockwebgame.domains.*;
import java.io.Serializable;
import java.util.ArrayList;
import com.vt.stockwebgame.domains.User;
import com.vt.stockwebgame.helpers.StockLookup;
import java.util.Collections;

/**
 *
 * @author painter
 */
public class StockManager implements Serializable {
    
    private User user;
    private String username;
    private String password;
    private ArrayList<User> activeUsers;
    
    public StockManager() {
        activeUsers = new ArrayList<User>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public ArrayList<User> getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(ArrayList<User> ActiveUsers) {
        this.activeUsers = ActiveUsers;
    }
    
    public String createUser() {
        activeUsers.add(user);
        return ""; 
    }
    
    public float getStockPrice(String symbol) {
        Stock s = StockLookup.loadStock(symbol);
        if (s == null) {
            return 0;
        }
        else {
            return s.getPrice();
        }
    }
    
    public String getStockData(String symbol) {
        StockChart s = StockLookup.loadStockChart(symbol);
        if (s == null) {
            return "";
        }
        else {
            return s.getChartJSON();
        }
    }
    
    public boolean checkStockExists(String symbol) {
        return StockLookup.loadStock(symbol) == null;
    }
    
    public ArrayList<User> getLeaderboard() {
        ArrayList<User> leaderboard = new ArrayList<User>(activeUsers);
        Collections.sort(leaderboard);
        return leaderboard;
    }
    
    //--------------------------------------------------------------------------
    
    public Boolean loginUser(String username, String password) {
        for (User u : activeUsers) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                user = u;
                return true;
            }
        }
        return false;
    }
    
    public Boolean logout() {
        if (user == null) return false;
        user = null;
        return true;
    }

    public Boolean signUp(User user) {
        for (User u : activeUsers) {
            if (u.getUsername().equals(u.getUsername())) {
                return false;
            }
        }
        activeUsers.add(user);
        return true;
    }

    public User findActiveUser(String username) {
        for (User u : activeUsers) {
            if (u.getUsername().equals(username)) return u;
        }
        return null;
    }
}