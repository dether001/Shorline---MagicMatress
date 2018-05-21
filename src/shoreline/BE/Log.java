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

    /**
     * @param name the name to set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    /**
     * @return the rating
     */
    public String getAction() {
        return action;
    }

    /**
     * @param rating the rating to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the prating
     */
    public String getDate() {
        return date;
    }

    /**
     * @param prating the prating to set
     */
    public void setDate(String date) {
        this.date = date;
    }

}
