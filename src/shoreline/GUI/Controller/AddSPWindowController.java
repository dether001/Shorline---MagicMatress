/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.GUI.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author atilk
 */
public class AddSPWindowController implements Initializable {

    @FXML
    private Button ChooseFile;
    @FXML
    private TextField PathField;
    public String path;
    @FXML
    private Button cancelBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleCancel(ActionEvent event) throws IOException 
    {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent Root = FXMLLoader.load(getClass().getResource("/shoreline/GUI/View/LoggedInWindow.fxml"));
        Scene scene = new Scene (Root);
        stage.setScene(scene);
        stage.setTitle("ShoreLine - Data Converter");
        stage.show();
    }
    
    @FXML
    private void chooseFileClicked(ActionEvent event)
    {
        Stage stage = new Stage(); 
        FileChooser fc = new FileChooser();
        
        FileChooser.ExtensionFilter xlsFilter = new 
        FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls");
        FileChooser.ExtensionFilter xlsxFilter = new 
        FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx");

        fc.getExtensionFilters().add(xlsFilter);
        fc.getExtensionFilters().add(xlsxFilter);
        
        File selectedFile = fc.showOpenDialog(stage);
        this.path = selectedFile.getAbsolutePath();
        PathField.setText(path); 
    }
    
}
