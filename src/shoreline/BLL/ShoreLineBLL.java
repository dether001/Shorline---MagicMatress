/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.BLL;


import java.io.IOException;
import java.util.List;
import javafx.collections.ObservableList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import shoreline.BE.JSonObject;
import shoreline.BE.Pattern;
import shoreline.BE.User;
import shoreline.DAL.DatabaseAL;
import shoreline.DAL.ExcellAL;
import shoreline.DAL.Logger;



/**
 *
 * @author Arman
 */
public class ShoreLineBLL {
    
    DatabaseAL dbal = new DatabaseAL();
    Logger logger = new Logger();
    ExcellAL excell = new ExcellAL();
    
    public User tryLogIn(User user) {
     
        
        return dbal.tryLogIn(user);
        
    }

    public void loginFLog(User user) {
      logger.loginFLog(user);
    }

    public void loginCLog(User user) {
        logger.loginCLog(user);
    }

    public List<String> makeComboboxes(String path) throws IOException, InvalidFormatException {
        
        return excell.makeComboboxes(path);
    }

    
    public ObservableList<JSonObject> read(List list, String path) throws IOException, InvalidFormatException {
         return excell.read(list, path);
    }

    public void savePatter(Pattern patter) {
        dbal.newPattern(patter);
    }

    public List<Integer> getExistingPattern(String selectedPattern) {
     
        return dbal.getExsistingPattern(selectedPattern);
    }

    public void convertLog(User user) {
       logger.convertLog(user);
    }

    public void convertWLog(User user) {
        logger.convertWsave(user);
    }

   



   
    
}
