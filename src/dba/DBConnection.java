/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Viktor
 */
public class DBConnection {
    
    private static Logger loggerErrorSaver = Logger.getLogger(DBConnection.class);
    
    //Method for Shoreline DB connection

    //private static DBConnection instance;
    public static Connection Shoreline() {
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://10.176.111.31:1433;databaseName=Shoreline;username=CS2017B_28_java;password=javajava";
            con = DriverManager.getConnection(url);
        } catch (ClassNotFoundException ex) {
             loggerErrorSaver.error("error while trying to connect to DatabaseShoreline: " + ex + ex);
        } catch (SQLException ex) {
             loggerErrorSaver.error("error while trying to connect to DatabaseShoreline: " + ex + ex);
        }
        
        return con;
    
    }
    
    //Method for ECompany DB connection
    
    public static Connection ECompany() {
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://10.176.111.31:1433;databaseName=ECompany;username=CS2017B_28_java;password=javajava";
            con = DriverManager.getConnection(url);
        } catch (ClassNotFoundException ex) {
             loggerErrorSaver.error("error while trying to connect to DatbaseEcompany: " + ex + ex);
        } catch (SQLException ex) {
            loggerErrorSaver.error("error while trying to connect to DatbaseEcompany: " + ex + ex);
        }
        
        return con;
    }
}
