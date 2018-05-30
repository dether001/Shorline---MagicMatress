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
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import shoreline.BE.User;
import shoreline.BLL.ShoreLineBLL;

/**
 *
 * @author dell
 */
public class UserLogs {
    
    private Connection con;
    private PreparedStatement pst;
    private static Logger loggerErrorSaver = getLogger(DatabaseAL.class);
    
    
       public void loginFLog (User user) {
        try {
            con = dba.DBConnection.Shoreline();
            pst = con.prepareStatement("insert into actionlog VALUES (?, 'failed to log in', CURRENT_TIMESTAMP, ? )");

            pst.setString(1, user.getName());
            pst.setInt(2, user.getSelectedCompany());
            pst.executeUpdate();
        } catch (SQLException ex) {
             loggerErrorSaver.error("error while savingFLOg: " + ex + ex);
        }
    }

    

    public void loginCLog (User user) {
        try {
            con = dba.DBConnection.Shoreline();
            pst = con.prepareStatement("insert into actionlog VALUES (?, 'logged in', CURRENT_TIMESTAMP, ? )");

            pst.setString(1, user.getName());
            pst.setInt(2, user.getSelectedCompany());
            pst.executeUpdate();
        } catch (SQLException ex) {
            loggerErrorSaver.error("error while saving Clog: " + ex + ex);
        }
    }
    
    
        public void convertLog (User user) {
        try {
            con = dba.DBConnection.Shoreline();
            pst = con.prepareStatement("insert into actionlog VALUES (?, 'converted on the spot', CURRENT_TIMESTAMP, ? )");

            pst.setString(1, user.getName());
            pst.setInt(2, user.getSelectedCompany());
            pst.executeUpdate();
        } catch (SQLException ex) {
            loggerErrorSaver.error("error while saving convertLog: " + ex + ex);
        }
    }
        
        
        public void convertWsave (User user) {
        try {
            con = dba.DBConnection.Shoreline();
            pst = con.prepareStatement("insert into actionlog VALUES (?, 'converted with a task linked to it', CURRENT_TIMESTAMP, ? )");

            pst.setString(1, user.getName());
            pst.setInt(2, user.getSelectedCompany());
            pst.executeUpdate();
        } catch (SQLException ex) {
             loggerErrorSaver.error("error while saving saveWithSave: " + ex + ex);
        }
    } 
        
        public void errorLog (User user) {
        try {
            con = dba.DBConnection.Shoreline();
            pst = con.prepareStatement("insert into actionlog VALUES (?, 'had an error', CURRENT_TIMESTAMP, ? )");

            pst.setString(1, user.getName());
            pst.setInt(2, user.getSelectedCompany());
            pst.executeUpdate();
        } catch (SQLException ex) {
             loggerErrorSaver.error("error while saving errorLog: " + ex + ex);
        }
    } 

   
}
