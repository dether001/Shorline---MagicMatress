/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import shoreline.BE.User;
import shoreline.LoginWindowController;

/**
 *
 * @author Arman
 */
public class DatabaseAL {
    
    private Connection con;

    public User tryLogIn(User user) {
        
        if (user.getSelectedCompany() == 1) {
            con = dba.DBConnection.Shoreline();
        }
        else if (user.getSelectedCompany() == 2) {
            con = dba.DBConnection.ECompany();
        }
        
        
        return getID(user);
        
        
    }
    
    private User getPW(User user){
            
        try {
            PreparedStatement pst = con.prepareStatement("Select password from users where login = ?");
            pst.setString(1,user.getPassword());
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
            
            user.setId(1);
            rs.close();
            }
            
            
                    
        } catch (SQLException ex) {
            Logger.getLogger(LoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
        
    }
    
    private User getID(User user){
            
        try {
            PreparedStatement pst = con.prepareStatement("Select login from users where login = ?");
            pst.setString(1,user.getName());
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
            user = getPW(user);
            rs.close();
            }
                    
        } catch (SQLException ex) {
            Logger.getLogger(LoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return user;
    }
    
    
    
}
