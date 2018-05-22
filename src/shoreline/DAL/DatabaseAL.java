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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import shoreline.BE.Pattern;
import shoreline.BE.User;
import shoreline.GUI.Controller.AddSPWindowController;
import shoreline.GUI.Controller.LoginWindowController;

/**
 *
 * @author Arman
 */
public class DatabaseAL {
    
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

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
    
        public void newPattern (Pattern pattern) {
        try {
            con = dba.DBConnection.Shoreline();
            pst = con.prepareStatement("insert into Patterns VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, pattern.getCreatedBy_User());
            pst.setInt(2, pattern.getAssestSeriliaNum());
            pst.setInt(3, pattern.getType());
            pst.setInt(4, pattern.getExternalWorkOrder());
            pst.setInt(5, pattern.getStatus());
            pst.setInt(6, pattern.getUserStatus());
            pst.setInt(7, pattern.getCreatedBy());
            pst.setInt(8, pattern.getName());
            pst.setInt(9, pattern.getPriority());
            pst.setInt(10, pattern.getStatus());
            pst.setInt(11, pattern.getLatestFinishDate());
            pst.setInt(12, pattern.getEarliestStartDate());
            pst.setInt(13, pattern.getLatestStartDate());
            pst.setInt(14, pattern.getEstimatedTime());
            pst.setString(15, pattern.getPatternName());
            
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

    public List<Integer> getExsistingPattern(String selectedPattern) {
       List<Integer> listOfIDs = new ArrayList<Integer>();
        try {
            con = dba.DBConnection.Shoreline();
            String sql = "select AssetSerialNum, Type, ExternalWorkOrder, SystemStatus, userStatus, CreatedBy, Name, Priority, Status, LatestFinishDate, EarliestStartDate, LatestStartDate, EstimatedTime FROM Patterns WHERE PatternName = ?;";
            pst=con.prepareStatement(sql);
            pst.setString(1, selectedPattern);
            rs = pst.executeQuery();
            while(rs.next()) {
            
            listOfIDs.add(Integer.valueOf(rs.getString(1)));
            listOfIDs.add(Integer.valueOf(rs.getString(2)));
            listOfIDs.add(Integer.valueOf(rs.getString(3)));
            listOfIDs.add(Integer.valueOf(rs.getString(4)));
            listOfIDs.add(Integer.valueOf(rs.getString(5)));
            listOfIDs.add(Integer.valueOf(rs.getString(6)));
            listOfIDs.add(Integer.valueOf(rs.getString(7)));
            listOfIDs.add(Integer.valueOf(rs.getString(8)));
            listOfIDs.add(Integer.valueOf(rs.getString(9)));
            listOfIDs.add(Integer.valueOf(rs.getString(10)));
            listOfIDs.add(Integer.valueOf(rs.getString(11)));
            listOfIDs.add(Integer.valueOf(rs.getString(12)));
            listOfIDs.add(Integer.valueOf(rs.getString(13)));     
            
            
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddSPWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listOfIDs;
    }
    
}
