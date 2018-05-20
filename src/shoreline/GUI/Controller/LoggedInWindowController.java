/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.GUI.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import shoreline.BE.User;

/**
 * FXML Controller class
 *
 * @author atilk
 */
public class LoggedInWindowController implements Initializable {

    @FXML
    private Button LogOutBtn;
    @FXML
    private Button AddSpBtn;
    @FXML
    private Button ViewLogBtn;
    @FXML
    private Label loggedInlbl;
    @FXML
    private Label dateLbl;
    
    User user;
    
    LoginWindowController LogInWin;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
     public void setMainViewCont(LoginWindowController LogInWin) {
        this.LogInWin=LogInWin;
        user = LogInWin.returnUser();
        
        
    }
    
    
    
    //Event Handlers.
    @FXML
    private void handleCancel(ActionEvent event) throws IOException 
    {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent Root = FXMLLoader.load(getClass().getResource("/shoreline/GUI/View/SelectCompany.fxml"));
        Scene scene = new Scene (Root);
        stage.setScene(scene);
        stage.setTitle("ShoreLine - Data Converter");
        stage.show();
    
    }
    @FXML
    private void handleNewPattern (ActionEvent event) throws IOException 
    {       System.out.println(user.getName());
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/shoreline/GUI/View/NewPatternWindow.fxml"));
        Parent root1 = loader.load();
        NewPatternWindowController contrl = loader.getController();
        contrl.setMainViewCont(this);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root1));
        stage.show();
    }
    
    @FXML
    private void handleExistingPattern (ActionEvent event) throws IOException 
    {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent Root = FXMLLoader.load(getClass().getResource("/shoreline/GUI/View/AddSPWindow.fxml"));
        Scene scene = new Scene (Root);
        stage.setScene(scene);
        stage.setTitle("ShoreLine - Data Converter");
        stage.show();
    }
    
    @FXML
    private void handleLog (ActionEvent event) throws IOException
    {
        //Add switch for company /Shoreline /other company
        Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
        Parent Root = FXMLLoader.load(getClass().getResource("/shoreline/GUI/View/SLLogWindow.fxml"));
        Scene scene = new Scene (Root);
        stage.setScene(scene);
        stage.setTitle("ShoreLine - Data Converter");
        stage.show();
    }

   
    
    public User returnUser(){
    
    return user;
    }
}
