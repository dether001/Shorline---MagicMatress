/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.GUI.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import shoreline.BE.JSonObject;

/**
 *
 * @author Arman
 */
public class Model {
    
    private ObservableList <String> listOfJasons = FXCollections.observableArrayList();
    
    //FileWriter method. Used to create the output JSon file "testfile.json.
    //TODO create filesaver dialogue for dir choosing UX.
    public void convert(ObservableList<JSonObject> listData) throws IOException{
         try (FileWriter file = new FileWriter("testfile.json")) 
         {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(listData);
            Platform.runLater(new Runnable() 
            {
                public void run() 
                {
                    Stage stage2 = new Stage();
                    FileChooser fc = new FileChooser();
                    FileChooser.ExtensionFilter jsonFilter = new 
                    FileChooser.ExtensionFilter("Json files (*.json)", "*.json");
                    fc.getExtensionFilters().add(jsonFilter);
                    File savepath = fc.showSaveDialog(stage2);
                    if (file != null) 
                    {
                        try 
                        {
                            saveTextToFile(json, savepath);
                        } 
                        catch (FileNotFoundException ex) 
                        {
                            
                            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            );
            listOfJasons.add(json);
         }
    }
    
    private void saveTextToFile(String content, File file) throws FileNotFoundException
    {
        PrintWriter writer;
        writer = new PrintWriter(file);
        writer.println(content);
        writer.close();
    } 

    
}
