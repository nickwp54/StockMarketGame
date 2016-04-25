/*
 * Created by Nicholas Phillpott on 2016.04.24  * 
 * Copyright © 2016 Nicholas Phillpott. All rights reserved. * 
 */
package com.vt.stockwebgame.manager;

import java.io.Serializable;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.util.ArrayList;
import com.vt.stockwebgame.domains.User;
import com.vt.stockwebgame.helpers.StockLookup;

/**
 *
 * @author painter
 */
@Named(value = "stockManager")
@SessionScoped
public class StockManager implements Serializable {
    
    private User user;
    private User login; 
    private ArrayList<User> ActiveUsers; 
    private String statusMessage; 
    
    public StockManager() {
        login = new User(); 
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public User getLogin() {
        return user;
    }

    public void setLogin(User user) {
        this.user = user;
    }

    public ArrayList<User> getActiveUsers() {
        return ActiveUsers;
    }

    public void setActiveUsers(ArrayList<User> ActiveUsers) {
        this.ActiveUsers = ActiveUsers;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    
    public String createUser() {
        ActiveUsers.add(user);
        return ""; 
    }
    
    public float getStockPrice(String symbol) {
        return StockLookup.loadStock(symbol).getPrice();
    }
    
    public String testit() {
        return "this is a test";
    }
    
    //--------------------------------------------------------------------------
    
    public String loginUser() {
        
        for (User u : ActiveUsers) {
            if (u.getUsername().equals(login.getUsername()) && u.getPassword().equals(login.getPassword())) {
                user = u; 
            }
            else {
                statusMessage = "Username of Password Wrong";
                return "";
            }
        }
        return "Account";
    }
    
    public String logout() {
        user = null; 
        return "";
    }
    
    public String prepareCreateUser() {
        user = new User(); 
        return "CreateUser";
    }
}
