/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.GUI.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
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
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import shoreline.BE.JSonObject;
import shoreline.BE.Pattern;
import shoreline.BE.User;
import shoreline.BE.Log;
import shoreline.BLL.ShoreLineBLL;
import shoreline.GUI.Controller.LoginWindowController;

/**
 * FXML Controller class
 *
 * @author atilk
 */
public class NewPatternWindowController implements Initializable {


    private String path;
    @FXML
    private Button chooseFile;
    @FXML
    private TextField PathField;
    @FXML
    private ComboBox<String> boxAssestSerialNum;
    @FXML
    private ComboBox<String> boxType;
    @FXML
    private ComboBox<String> boxExternalWorkOrder;
    @FXML
    private ComboBox<String> BoxSystemStatus;
    @FXML
    private ComboBox<String> boxUserStatus;
    @FXML
    private ComboBox<String> boxCreatedBy;
    @FXML
    private ComboBox<String> boxName;
    @FXML
    private ComboBox<String> boxPriority;
    @FXML
    private ComboBox<String> boxStatus;
    @FXML
    private ComboBox<String> boxLastestFinishDate;
    @FXML
    private ComboBox<String> boxEarliestStartDate;
    @FXML
    private ComboBox<String> boxLatestStartDate;
    @FXML
    private ComboBox<String> boxEstimatedTime;
    @FXML
    private Button cancelBtn;   
    @FXML
    private Button buttonConvert;
    
    private ObservableList <JSonObject> listData =  FXCollections.observableArrayList();
    
    private ObservableList <String> listOfJasons = FXCollections.observableArrayList();
    @FXML
    private TextField newPatternTxt;
    @FXML
    private Button btnSaveConvert;
    private PreparedStatement pst;
    private Connection con;
    
    User user = new User();
    Log log;
    LoginWindowController LWC;
    LoggedInWindowController lgc = new LoggedInWindowController();
    ShoreLineBLL bll = new ShoreLineBLL();
    Model model = new Model();
    private static Logger loggerErrorSaver = Logger.getLogger(NewPatternWindowController.class);

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }
      
    
    
    
    @FXML
    private void handleCancel(ActionEvent event) 
    {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void chooseFileClicked(ActionEvent event) 
    {
        try {
            Stage stage = new Stage(); 
        FileChooser fc = new FileChooser();
        
        FileChooser.ExtensionFilter xlsxFilter = new 
        FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx");
        FileChooser.ExtensionFilter xlsFilter = new 
        FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls");

        fc.getExtensionFilters().add(xlsxFilter);
        fc.getExtensionFilters().add(xlsFilter);
        
        File selectedFile = fc.showOpenDialog(stage);
        path = selectedFile.getAbsolutePath();
        PathField.setText(path); 
        System.out.println(path);
        makeComboBox(path);
            
        } catch (Exception ex) {
            loggerErrorSaver.error("error while choosing file: " + ex + ex);
            bll.taskException(user);
        }
        
        
    }

    @FXML
    private void buttonConvertAction(ActionEvent event) 
    {
        startTask("convert");
        // we forgot to add the log method here
    }
    
    @FXML
    private void buttonActionSaveConvert(ActionEvent event) 
    {
        startTask("save");
        bll.convertWLog(user);
    }

    private void makeComboBox(String path) {
        clearComboBox();
        ObservableList<String>rowList=FXCollections.observableArrayList();
        rowList.addAll(bll.makeComboboxes(path));
        boxAssestSerialNum.getItems().addAll(rowList);
        boxType.getItems().addAll(rowList);
        boxExternalWorkOrder.getItems().addAll(rowList);
        BoxSystemStatus.getItems().addAll(rowList);
        boxUserStatus.getItems().addAll(rowList);
        boxCreatedBy.getItems().addAll(rowList);
        boxName.getItems().addAll(rowList);
        boxPriority.getItems().addAll(rowList);
        boxStatus.getItems().addAll(rowList);
        boxLastestFinishDate.getItems().addAll(rowList);
        boxEarliestStartDate.getItems().addAll(rowList);
        boxLatestStartDate.getItems().addAll(rowList);
        boxEstimatedTime.getItems().addAll(rowList);
    }
    
    //Clears all comboboxes.
    private void clearComboBox()
    {
        boxAssestSerialNum.getItems().clear();
        boxType.getItems().clear();
        boxExternalWorkOrder.getItems().clear();
        BoxSystemStatus.getItems().clear();
        boxUserStatus.getItems().clear();
        boxCreatedBy.getItems().clear();
        boxName.getItems().clear();
        boxPriority.getItems().clear();
        boxStatus.getItems().clear();
        boxLastestFinishDate.getItems().clear();
        boxEarliestStartDate.getItems().clear();
        boxLatestStartDate.getItems().clear();
        boxEstimatedTime.getItems().clear();
    }
    
    //Calls to BLL Read method to read the given file with the given list of headers.
    //It will skip collumns not contained in the list regarding index number.

     public void Read(List list){
        
        try {
            ShoreLineBLL bll = new ShoreLineBLL();
            
            listData = bll.read(list, path);
            
            model.convert(listData);
            
        } catch (IOException ex) {
             loggerErrorSaver.error("error while trying converting: " + ex + ex);
             bll.taskException(user);
        }

    }

     
    
    // setUser method is used all around our program as a "pre-initalization" method
    public void setUser (User user)
    {
        this.user = user;
    }

    public void createPatternandSave()
    {
        // a little naming error here
        Pattern patter = new Pattern();
        patter.setAssestSeriliaNum(boxAssestSerialNum.getSelectionModel().getSelectedIndex());
        patter.setType((boxType.getSelectionModel().getSelectedIndex()));
        patter.setExternalWorkOrder(boxExternalWorkOrder.getSelectionModel().getSelectedIndex());
        patter.setStatus(BoxSystemStatus.getSelectionModel().getSelectedIndex());
        patter.setUserStatus(boxUserStatus.getSelectionModel().getSelectedIndex());
        patter.setCreatedBy(boxCreatedBy.getSelectionModel().getSelectedIndex());
        patter.setName(boxName.getSelectionModel().getSelectedIndex());
        patter.setPriority(boxPriority.getSelectionModel().getSelectedIndex());
        patter.setStatus(boxStatus.getSelectionModel().getSelectedIndex());
        patter.setLatestFinishDate(boxLastestFinishDate.getSelectionModel().getSelectedIndex());
        patter.setLatestFinishDate(boxLastestFinishDate.getSelectionModel().getSelectedIndex());
        patter.setEarliestStartDate(boxEarliestStartDate.getSelectionModel().getSelectedIndex());
        patter.setLatestStartDate(boxLatestStartDate.getSelectionModel().getSelectedIndex());
        patter.setEstimatedTime(boxEstimatedTime.getSelectionModel().getSelectedIndex());
        patter.setCreatedBy_User(user.getName());
        patter.setPatternName(newPatternTxt.getText());
        bll.convertWLog(user);
        bll.savePatter(patter);
    }
    public List<Integer> createList()
    {
        List<Integer> list = new ArrayList<Integer>();
        list.add(boxAssestSerialNum.getSelectionModel().getSelectedIndex());
        list.add(boxType.getSelectionModel().getSelectedIndex());
        list.add(boxExternalWorkOrder.getSelectionModel().getSelectedIndex());
        list.add(BoxSystemStatus.getSelectionModel().getSelectedIndex());
        list.add(boxUserStatus.getSelectionModel().getSelectedIndex());
        list.add(boxCreatedBy.getSelectionModel().getSelectedIndex());
        list.add(boxName.getSelectionModel().getSelectedIndex());
        list.add(boxPriority.getSelectionModel().getSelectedIndex());
        list.add(boxStatus.getSelectionModel().getSelectedIndex());
        list.add(boxLastestFinishDate.getSelectionModel().getSelectedIndex());
        list.add(boxEarliestStartDate.getSelectionModel().getSelectedIndex());
        list.add(boxLatestStartDate.getSelectionModel().getSelectedIndex());
        list.add(boxEstimatedTime.getSelectionModel().getSelectedIndex());
        return list;
    }
 public void setConnection()
 {
                         switch (user.getSelectedCompany()) 
                        {
                            case 1: 
                                con = dba.DBConnection.Shoreline();
                            break;
            
                            case 2: 
                                con = dba.DBConnection.ECompany();
                                System.out.println(newPatternTxt.toString());
                                System.out.println(user.getName());
                                System.out.println(path);
                            break;
                        }
 }
    public void saveTask()
    {
        try 
        {
                        pst = con.prepareStatement("INSERT INTO tasks VALUES(?, ?, ?)");
                        pst.setString(1, user.getName());
                        pst.setString(2, newPatternTxt.getText());
                        pst.setString(3, path);
                        pst.executeUpdate();
                        System.out.println(user.getSelectedCompany());

                    } catch (SQLException ex) {
                        loggerErrorSaver.error("error while savingTask: " + ex + ex);
                    }
    }
    public void startTask(String string)
    { 
        if(string == "save")
        {
            Runnable task = new Runnable()
            {
                @Override
                public void run() 
                {

                    try 
                    {
                        createPatternandSave();
                        // it's called twice by mistake
                        createList();
                        setConnection();
                        saveTask();
                        Read(createList());
                    } 
                    catch (Exception ex) 
                    {
                        bll.taskException(user);
                        loggerErrorSaver.error("error while savingTask: " + ex + ex);
                    }
      
                }   
            };
            Thread ConvThread = new Thread(task);
            ConvThread.setDaemon(true);
            ConvThread.start();
        }
        if(string=="convert")
        {
            Runnable task = new Runnable()
            {
                @Override
                public void run() 
                {
                    try 
                    {
                        // it's called twice by mistake
                        createList();
                        Read(createList());
                        Stage stage = (Stage) buttonConvert.getScene().getWindow();
                        stage.close();
                        
                    } 
                    catch (Exception ex) 
                    {
                        bll.taskException(user);
                        loggerErrorSaver.error("error while converting: " + ex + ex);
                    }
                }   
            };
            Thread ConvThread = new Thread(task);
            ConvThread.setDaemon(true);
            ConvThread.start();
        }
    }     
}
