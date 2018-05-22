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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
    
    User user;
    Log log;
    LoginWindowController LWC;
    LoggedInWindowController lgc;
    ShoreLineBLL bll = new ShoreLineBLL();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
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
    private void chooseFileClicked(ActionEvent event) throws Exception
    {
        Stage stage = new Stage(); 
        FileChooser fc = new FileChooser();
        
        FileChooser.ExtensionFilter xlsxFilter = new 
        FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx");
        FileChooser.ExtensionFilter xlsFilter = new 
        FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls");

        fc.getExtensionFilters().add(xlsFilter);
        fc.getExtensionFilters().add(xlsxFilter);
        
        File selectedFile = fc.showOpenDialog(stage);
        path = selectedFile.getAbsolutePath();
        PathField.setText(path); 
        System.out.println(path);
        makeComboBox(path);
        
    }

    @FXML
    private void buttonConvertAction(ActionEvent event) throws InvalidFormatException, Exception 
    {
        String string = new String();
        string = "convert";
        startTask(string);
    }
    
    @FXML
    private void buttonActionSaveConvert(ActionEvent event) throws InvalidFormatException, Exception 
    {
        String string = new String();
        string = "save";
        startTask(string);
        bll.convertWLog(user);
    }
    
    private void makeComboBox(String path) throws Exception{
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
    private void clearComboBox(){
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
    public void Read(List list) throws IOException, InvalidFormatException, Exception{
        
        
        
        Workbook workbook = WorkbookFactory.create(new File(path)); //finds file
        Sheet sheet = workbook.getSheetAt(0);  // gets sheet
        DataFormatter dataFormatter = new DataFormatter();  // formats data
        
        ShoreLineBLL bll = new ShoreLineBLL();
        
        listData = bll.read(list, path);
        convert();
        
       
        
    }
     
    //FileWriter method. Used to create the output JSon file "testfile.json.
    //TODO create filesaver dialogue for dir choosing UX.
    public void convert() throws IOException{
         try (FileWriter file = new FileWriter("testfile.json")) {
            
            for (JSonObject jSonObject : listData) {

               Gson gson = new GsonBuilder().setPrettyPrinting().create();
               String json = gson.toJson(jSonObject);
             
               
                    
                
               file.write(json);
               file.flush();


                listOfJasons.add(json);
                
            } 
        }
    }

    public void setUser (User user)
    {
        this.user = user;
    }

    public void createPatternandSave()
    {
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
        
        bll.savePatter(patter);
    }
    public void CreateList() throws Exception
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
        Read(list);
    }

    public void startTask(String string)
    { if(string == "save")
    {
        Runnable task = new Runnable()
        {
            @Override
            public void run() 
            {
                try {
                    saveTask();
                } catch (Exception ex) {
                    Logger.getLogger(NewPatternWindowController.class.getName()).log(Level.SEVERE, null, ex);
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
                try {
                    convertTask();
                } catch (Exception ex) {
                    Logger.getLogger(NewPatternWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        };
        Thread ConvThread = new Thread(task);
        ConvThread.setDaemon(true);
        ConvThread.start();
      }
        
    
    }
    
    public void saveTask() throws Exception
    {
        createPatternandSave();
        CreateList();
        System.out.println("Inside Runtask SaveConvert - Success");
    }
    
    public void convertTask() throws Exception
    {
        CreateList();
        System.out.println("Inside Runtask Convert - Success");
    }
    
    
    
    
    
    
    
}
