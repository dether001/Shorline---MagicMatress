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

/**
 * FXML Controller class
 *
 * @author dell
 */
public class LoginWindowController implements Initializable {

    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_pw;
    @FXML
    private Button btn_Login;
    @FXML
    private Button btn_Cacel;
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    public int cid;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (cid == 1) {
            con = dba.DBConnection.Shoreline();
        }
        else if (cid == 0) {
            System.out.println("Something went wrong");
        }
        
    }    

    //Event Handlers
    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        if (cid == 1) {
            con = dba.DBConnection.Shoreline();
        }
        else if (cid == 2) {
            con = dba.DBConnection.ECompany();
        }
        if (txt_id.getText().equals(getID()) && txt_pw.getText().equals(getPW())){
            System.out.println("Succes!");
            loginCLog();
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            loginCLog();
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
    private String getID(){
            String id = "";
        try {
            pst = con.prepareStatement("Select login from users where login = ?");
            pst.setString(1,txt_id.getText());
            rs = pst.executeQuery();
            if(rs.next())
            id = rs.getString(1);
            rs.close();
                    
        } catch (SQLException ex) {
            Logger.getLogger(LoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    private String getPW(){
            String pw = "";
        try {
            pst = con.prepareStatement("Select password from users where login = ?");
            pst.setString(1,txt_id.getText());
            rs = pst.executeQuery();
            if(rs.next())
            pw = rs.getString(1);
            rs.close();
                    
        } catch (SQLException ex) {
            Logger.getLogger(LoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pw;
    }
    
    public void setCID(int cid){
        this.cid = cid;;
    }
    

    private void loginFLog () {
        try {
            pst = con.prepareStatement("insert into actionlog VALUES (?, 'failed to log in', CURRENT_TIMESTAMP )");;

            pst.setString(1, txt_id.getText());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    private void loginCLog () {
        try {
            pst = con.prepareStatement("insert into actionlog VALUES (?, 'logged in', CURRENT_TIMESTAMP )");

            pst.setString(1, txt_id.getText());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
