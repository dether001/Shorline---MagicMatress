/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import shoreline.BE.User;
import shoreline.BLL.ShoreLineBLL;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class LoginWindowController implements Initializable {

    
    @FXML
    private TextField txt_pw;
    @FXML
    private Button btn_Login;
    @FXML
    private Button btn_Cacel;
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    User user = new User();
    private TextField txt_name;
    @FXML
    private TextField txt_id;
    ShoreLineBLL bll = new ShoreLineBLL();


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
        
    }    

    //Event Handlers
    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        
        user.setName(txt_id.getText());
        user.setPassword(txt_pw.getText());
        user.setId(-1);
        
        
        user = bll.tryLogIn(user);
        
        
        if (user.getId()!=-1){
            System.out.println("Succes!");
            loginCLog();
            
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent Root = FXMLLoader.load(getClass().getResource("LoggedInWindow.fxml"));
        Scene scene = new Scene (Root);
        stage.setScene(scene);
        stage.setTitle("ShoreLine - Data Converter");
        stage.show();
        
        }
        else {
            loginFLog();
            
            Alert alert = new Alert(Alert.AlertType.NONE,"Invalid ID or Password",ButtonType.OK);
            alert.setTitle("Invalid login info");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleCancel(ActionEvent event) throws IOException 
    {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent Root = FXMLLoader.load(getClass().getResource("SelectCompany.fxml"));
        Scene scene = new Scene (Root);
        stage.setScene(scene);
        stage.setTitle("ShoreLine - Data Converter");
        stage.show();
    
    }
    
    @FXML
    public void onEnter(ActionEvent event) throws IOException
    {
           handleLogin(event); 
    }
    

    
 
    
    public void setCID(User user){
        this.user = user;
    }
    

    private void loginFLog () {
        bll.loginFLog(user);
    }
    

    private void loginCLog () {
       bll.loginCLog(user);
    }
}
