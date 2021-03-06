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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.apache.log4j.Logger;
import shoreline.BE.Pattern;
import shoreline.BE.Tasks;
import shoreline.BE.User;
import shoreline.BLL.ShoreLineBLL;
import shoreline.GUI.Controller.AddSPWindowController;
import shoreline.GUI.Controller.LoggedInWindowController;
import shoreline.GUI.Controller.LoginWindowController;

/**
 *
 * @author Arman
 */
public class DatabaseAL {
    
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private User user;
    static int CID;
    private static Logger loggerErrorSaver = Logger.getLogger(DatabaseAL.class);
    UserLogs userLogs = new UserLogs();
    

    
    public User tryLogIn(User user) {
        
        if (user.getSelectedCompany() == 1) {
            con = dba.DBConnection.Shoreline();
            CID = 1;
        }
        else if (user.getSelectedCompany() == 2) {
            con = dba.DBConnection.ECompany();
            CID = 2;
        }
        return getID(user);
        
        
    }
    
    private User getPW(User user){
            
        try {
            PreparedStatement pst = con.prepareStatement("Select password from users where login = ?");
            pst.setString(1,user.getName());
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                String pass = rs.getString(1);
            //We had an error with case sensitivity, and we left it there, but now it works with 'equals' as intentded
            if(user.getPassword().equalsIgnoreCase(pass)){
            // setId is used instead of boolean by mistake
            user.setId(1);}
            rs.close();
            }
            
            
                    
        } catch (SQLException ex) {
          loggerErrorSaver.error("error while getting password: " + ex + ex);
          userLogs.errorLog(user);
        }
        return user;
        
    }
    
    private User getID(User user){
            User newuser = new User();
        try {
            
            PreparedStatement pst = con.prepareStatement("Select login from users where login = ?");
            pst.setString(1,user.getName());
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
            newuser = getPW(user);
            rs.close();
                System.out.println("getID method " + user.getName());
            }
                    
        } catch (SQLException ex) {
         loggerErrorSaver.error("error while getting ID(username): " + ex + ex);
         userLogs.errorLog(user);
        }
       
        return newuser;
    }
        
        // Gets values out of Pattern BE, and then loads it into the DB
       public void newPattern (Pattern pattern) {
        try {
           if (CID == 1) {
               con = dba.DBConnection.Shoreline();
           }
           else if (CID == 2) {
               con = dba.DBConnection.ECompany();
           }
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
            loggerErrorSaver.error("error while  SavingNewPattern(new pattern): " + ex + ex);
            userLogs.errorLog(user);
        }
        }

    // Quieries the DB for all patern info with a given name, and loads it into a list
    public List<Integer> getExsistingPattern(String selectedPattern) {
       List<Integer> listOfIDs = new ArrayList<Integer>();
        try {
           if (CID == 1) {
               con = dba.DBConnection.Shoreline();
           }
           else if (CID == 2) {
               con = dba.DBConnection.ECompany();
           }
            String sql = "select AssetSerialNum, Type, ExternalWorkOrder, SystemStatus, userStatus, CreatedBy, Name, Priority, Status, LatestFinishDate, EarliestStartDate, LatestStartDate, EstimatedTime FROM Patterns WHERE PatternName = ?;";
            pst=con.prepareStatement(sql);
            System.out.println("selected pattern is her " + selectedPattern);
            pst.setString(1, selectedPattern);
            
            System.out.println("selected pattern is not here " + selectedPattern);
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
             loggerErrorSaver.error("error while gettingExsistingPatternsIDLIST: " + ex + ex);
            userLogs.errorLog(user);
        }
        
        return listOfIDs;
    }

    //Queries the DB for information about all tasks, and loads it into a List
    public List<Tasks> loadTasks() {
           if (CID == 1) {
               con = dba.DBConnection.Shoreline();
           }
           else if (CID == 2) {
               con = dba.DBConnection.ECompany();
           }
        ArrayList<Tasks> taskList = new ArrayList<Tasks>();
            try {
            pst = con.prepareStatement("Select usedPattern, path FROM tasks");
            rs = pst.executeQuery();
            while (rs.next()) {
                Tasks task = new Tasks(rs.getString(1), rs.getString(2));
                taskList.add(task);
            }
        } catch (SQLException ex) {
            loggerErrorSaver.error("error while getting UsedPatterns: " + ex + ex);
            userLogs.errorLog(user);
        }
            return taskList;
    }
    
    //Queries the DB for all patterns, and loads it into an Observable List
    public List<String> loadExsistingPatterns() {
        // we should've renamed the task
        ArrayList<String> taskList = new ArrayList<String>();
       try {
           if (CID == 1) {
               con = dba.DBConnection.Shoreline();
           }
           else if (CID == 2) {
               con = dba.DBConnection.ECompany();
           }
            String sql = "select PatternName from Patterns";
            pst=con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            while(rs.next()) {
                taskList.add(rs.getString("PatternName"));
            }   } catch (SQLException ex) {
             loggerErrorSaver.error("error while getting ExsistingPatternsLISTofNames: " + ex + ex);
             userLogs.errorLog(user);
        }
       return taskList;
       
       
    }
    
}
