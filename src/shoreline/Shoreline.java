/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author dell
 */
public class Shoreline extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
<<<<<<< HEAD
        Font.loadFont(Shoreline.class.getResource("/shoreline/GUI/View/Images/Neuton-Extrabold.ttf").toExternalForm(), 10);
        Parent root = FXMLLoader.load(getClass().getResource("/shoreline/GUI/View/SelectCompany.fxml"));
=======
        Parent root = FXMLLoader.load(getClass().getResource("/shoreline/GUI/View/LoginWindow.fxml"));
>>>>>>> 1dad1543b9c6b0c700402dcd5de0972700ac784c
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("ShoreLine - Data Converter");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
