/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.BE;

/**
 *
 * @author Viktor
 */
public class Log {
    
    private String user_name;
    private String action;
    private String date;
    
    public Log (String user_name, String action, String date) {
        this.user_name = user_name;
        this.action = action;
        this.date = date;
    }

    
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
