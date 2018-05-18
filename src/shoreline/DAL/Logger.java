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
import shoreline.BLL.ShoreLineBLL;

/**
 *
 * @author dell
 */
public class Logger {
    
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    
        private void loginFLog () {
        try {
            pst = con.prepareStatement("insert into actionlog VALUES (?, 'failed to log in', CURRENT_TIMESTAMP, ? )");;

            pst.setString(1, User.getName());
            pst.setInt(2, cid);
            pst.executeUpdate();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ShoreLineBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    private void loginCLog () {
        try {
            pst = con.prepareStatement("insert into actionlog VALUES (?, 'logged in', CURRENT_TIMESTAMP, ? )");

            pst.setString(1, User.getName());
            pst.setInt(2, cid);
            pst.executeUpdate();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ShoreLineBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
        private void convert () {
        try {
            pst = con.prepareStatement("insert into actionlog VALUES (?, 'converted on the spot', CURRENT_TIMESTAMP, ? )");

            pst.setString(1, User.getName());
            pst.setInt(2, cid);
            pst.executeUpdate();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ShoreLineBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        
        private void convertWsave () {
        try {
            pst = con.prepareStatement("insert into actionlog VALUES (?, 'converted with a task linked to it', CURRENT_TIMESTAMP, ? )");

            pst.setString(1, User.getName());
            pst.setInt(2, cid);
            pst.executeUpdate();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ShoreLineBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}