/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.GUI.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import shoreline.BE.JSonObject;
import shoreline.BE.User;
import shoreline.BLL.ShoreLineBLL;

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
    @FXML
    private ComboBox<String> patternBox;
    @FXML
    private Button convertBtn;
    public String sPath;
    public String sPattern;
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    final ObservableList patterns= FXCollections.observableArrayList();
    @FXML
    private Button saveandConvertBtn;
    User user;

    List<Integer> listOfIDs = new ArrayList<Integer>();
    Model model = new Model();
    private static Logger loggerErrorSaver =getLogger(AddSPWindowController.class);
    

    private ObservableList <JSonObject> listData =  FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        con = dba.DBConnection.Shoreline();
        loadPatterns();
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
    {  try {
        
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
            
        } catch (Exception ex) {
            loggerErrorSaver.error("error while trying converting: " + ex + ex);
        }
        
    }
    
    private void loadPatterns() {
        
        patterns.addAll(bll.loadExsistingPatterns());
        patternBox.setItems(patterns);
    }   

    @FXML
    private void selectionHandler(ActionEvent event) {

        getItemsID();
    }
    
    ShoreLineBLL bll = new ShoreLineBLL();
    
    private void getItemsID(){
    
        String selectedPattern = patternBox.getSelectionModel().getSelectedItem();
        System.out.println(selectedPattern);
        listOfIDs = bll.getExistingPattern(selectedPattern);
    }
    
    
    public List<Integer> getList() {
        return listOfIDs;
    }
    
    public void Read(List list) throws IOException, InvalidFormatException, Exception{

        
        ShoreLineBLL bll = new ShoreLineBLL();
        
        listData = bll.read(list, path);
        model.convert(listData);
        
       
        
    }

    @FXML
    private void fuckingConvertpls(ActionEvent event) {
        try {
            Read(getList());
        } catch (InvalidFormatException ex) {
           loggerErrorSaver.error("error while trying converting: " + ex + ex);
        } catch (Exception ex) {
            loggerErrorSaver.error("error while trying converting: " + ex + ex);
        }
    }

    @FXML
    private void convertWSave(ActionEvent event) {
         con = dba.DBConnection.Shoreline();
            sPath = PathField.getText();
            sPattern = patternBox.getSelectionModel().getSelectedItem();
        try {
            pst = con.prepareStatement("INSERT INTO tasks VALUES(?, ?, ?)");
            pst.setString(1, user.getName());
            pst.setString(2, sPattern);
            pst.setString(3, sPath);
            pst.executeUpdate();
            Read(listOfIDs);
        } catch (SQLException ex) {
           loggerErrorSaver.error("error while trying convertingWithSave: " + ex + ex);
        }catch (InvalidFormatException ex) {
            loggerErrorSaver.error("error while trying convertingWithSave: " + ex + ex);
        } catch (Exception ex) {
            loggerErrorSaver.error("error while trying convertingWithSave: " + ex + ex);
        }
    }

    void setUser(User user) {
      this.user = user;
    }
}
