/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.GUI.Controller;


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
import javafx.stage.Modality;
import javafx.stage.Stage;
import shoreline.BE.User;
import shoreline.BLL.ShoreLineBLL;
import shoreline.GUI.Controller.NewPatternWindowController;

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
    @FXML
    private TextField txt_id;
    ShoreLineBLL bll = new ShoreLineBLL();
    public int SelectedCompany;
    @FXML
    private TextField cmp_id;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
        
    }    

    //Event Handlers
    
    
    //Checks if the company code if filled out or not, if it is, it sets the connection based on the information
    
    private void validateCID () {
        
        SelectedCompany = Integer.valueOf(cmp_id.getText());
        
        if(cmp_id.getText() == null || cmp_id.getText().trim().isEmpty()) 
        {
            Alert alert = new Alert(Alert.AlertType.NONE,"Company Code is empty. Please fill to log in !",ButtonType.OK);
            alert.setTitle("Empty Company Code");
            alert.showAndWait();
        }

        switch (SelectedCompany) {
            case 1: 
                con = dba.DBConnection.Shoreline();
                Login();
            break;
            
            case 2: 
                con = dba.DBConnection.ECompany();
                Login();
            break;
            
            default:
                Alert alert = new Alert(Alert.AlertType.NONE,"Invalid Company Code !",ButtonType.OK);
                alert.setTitle("Invalid Code");
                alert.showAndWait();
            break;
        
        }
    }
    
    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        validateCID();

    }
    
    private void Login() {
        user.setName(txt_id.getText());
        user.setPassword(txt_pw.getText());
        user.setId(-1);
        user.setSelectedCompany(SelectedCompany);    
        
        
        
        bll.tryLogIn(user);
        
        
        if (user.getId()!=-1){
            try {
                System.out.println(user.getSelectedCompany());
                loginCLog();
                
                
                
                //Add switch for company /Shoreline /other company
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/shoreline/GUI/View/LoggedInWindow.fxml"));
                Parent root = (Parent) loader.load();
                
                LoggedInWindowController ctrl = loader.getController();
                ctrl.setUser(user);
                ctrl.loadDataFromDB();
                Stage stage2 = new Stage();
                Stage stage = (Stage) btn_Login.getScene().getWindow();
                stage.close();
                stage2.setScene(new Scene(root));
                stage2.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        else {
            loginFLog();
            
            Alert alert = new Alert(Alert.AlertType.NONE,"Invalid ID or Password",ButtonType.OK);
            alert.setTitle("Invalid login info");
            txt_pw.clear();
            alert.showAndWait();
        }
    }
    
    @FXML
    private void Exit ()
    {
        Stage stage = (Stage) btn_Cacel.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void onEnter(ActionEvent event) throws IOException
    {    
        validateCID();
    }

    public void setCID(User user){
        this.user = user;
        
    }
    
    public User returnUser()
    {
    return user;
    }
    public void setCompany(int id)
    {
        this.SelectedCompany = id;
    }

    private void loginFLog () {
        bll.loginFLog(user);
    }
    

    
    private void loginCLog () {
       bll.loginCLog(user);
    }

    
}
