/*
 * Created by Nicholas Phillpott on 2016.04.24  * 
 * Copyright © 2016 Nicholas Phillpott. All rights reserved. * 
 */
package com.mycompany.stockwebgame.manager;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.util.ArrayList;
import com.mycompany.stockwebgame.domains.User;

/**
 *
 * @author painter
 */
@Named(value = "stockManager")
@SessionScoped
public class StockManager implements Serializable {
    
    private User user;
    private ArrayList<User> ActiveUsers; 
    
    
    
    public StockManager() {
    }
    
    public String loginUser(String username, String password) {
        
        for (User u : ActiveUsers) {
            if (u.getUsername().equals() && )
        }
        
        return "Account";
    }
    
    public String logout() {
        user = null; 
        return "";
    }
}
