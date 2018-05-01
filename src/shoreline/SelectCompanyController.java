/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class SelectCompanyController implements Initializable {


    public int company;
    @FXML
    private Button ShorelineBtn;
    @FXML
    private Button ECompanyBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void shrln_login(ActionEvent event) {
        try {
            company = 1;
            System.out.println(company);
            Stage stage = (Stage) ShorelineBtn.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
            Parent root = (Parent) loader.load();
            
            LoginWindowController lwController = loader.getController();
            lwController.setCID(company);
            Stage stage2 = new Stage();
            stage2.setScene(new Scene(root));
            stage2.show();
        } catch (IOException ex) {
            Logger.getLogger(SelectCompanyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ec_login(ActionEvent event) {
        try {
            company = 2;
            System.out.println(company);
            Stage stage = (Stage) ShorelineBtn.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
            Parent root = (Parent) loader.load();
            
            LoginWindowController lwController = loader.getController();
            lwController.setCID(company);
            Stage stage2 = new Stage();
            stage2.setScene(new Scene(root));
            stage2.show();
        } catch (IOException ex) {
            Logger.getLogger(SelectCompanyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
