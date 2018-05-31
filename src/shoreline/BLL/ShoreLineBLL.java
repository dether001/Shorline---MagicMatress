/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.BLL;


import java.io.IOException;
import java.util.List;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import shoreline.BE.JSonObject;
import shoreline.BE.Pattern;
import shoreline.BE.Tasks;
import shoreline.BE.User;
import shoreline.DAL.DatabaseAL;
import shoreline.DAL.ExcellAL;
import shoreline.DAL.UserLogs;



/**
 *
 * @author Arman
 */
public class ShoreLineBLL {
    
    private static Logger log = getLogger(ShoreLineBLL.class);
    DatabaseAL dbal = new DatabaseAL();
    UserLogs userLogs = new UserLogs();
    ExcellAL excell = new ExcellAL();
    
    public User tryLogIn(User user) {
     
        
        return dbal.tryLogIn(user);
        
    }

    public void loginFLog(User user) {
      userLogs.loginFLog(user);
    }

    public void loginCLog(User user) {
        userLogs.loginCLog(user);
    }

    public List<String> makeComboboxes(String path){
        
        return excell.makeComboboxes(path);
    }

    
    public ObservableList<JSonObject> read(List list, String path){
         return excell.read(list, path);
    }

    public void savePatter(Pattern patter) {
        dbal.newPattern(patter);
    }

    public List<Integer> getExistingPattern(String selectedPattern) {
     
        return dbal.getExsistingPattern(selectedPattern);
    }

    public void convertLog(User user) {
       userLogs.convertLog(user);
    }

    public void convertWLog(User user) {
        userLogs.convertWsave(user);
    }

    public List<Tasks> loadusedPatterns() {
        return dbal.loadusedPatterns();
    }

    public List<String> loadExsistingPatterns() {
       return dbal.loadExsistingPatterns();
    }
    
    public void taskException(User user){
    
        userLogs.errorLog(user);
    
    }

   



   
    
}
