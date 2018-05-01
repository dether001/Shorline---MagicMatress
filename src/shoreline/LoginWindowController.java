/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        con = dba.DBConnection.dbConnection();
    }    

    @FXML
    private void handleLogin(ActionEvent event) {
        System.out.println(txt_id.getText());
        System.out.println(getID());
        if (txt_id.getText().equals(getID()) && txt_pw.getText().equals(getPW())){
            System.out.println("Succes!");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.NONE,"Invalid ID or Password",ButtonType.OK);
            alert.setTitle("Invalid login info");
            alert.showAndWait();
        }
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

    @FXML
    private void handleCancel(ActionEvent event) {

    }
    
}
