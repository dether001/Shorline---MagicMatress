/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.BLL;


import shoreline.BE.User;
import shoreline.DAL.DatabaseAL;
import shoreline.DAL.Logger;



/**
 *
 * @author Arman
 */
public class ShoreLineBLL {
    
    DatabaseAL dbal = new DatabaseAL();
    Logger logger = new Logger();
    
    public User tryLogIn(User user) {
     
        
        return dbal.tryLongIn(user);
        
    }

    public void loginFLog(User user) {
      logger.loginFLog(user);
    }

    public void loginCLog(User user) {
        logger.loginCLog(user);
    }

   
    
}
