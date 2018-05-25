/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.GUI.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
         try (FileWriter file = new FileWriter("testfile.json")) {
             

               Gson gson = new GsonBuilder().setPrettyPrinting().create();
               String json = gson.toJson(listData);
               
               
               
                    
               System.out.println(json);
               file.write(json);
               file.flush();


              listOfJasons.add(json);
        }
    }
    
}
