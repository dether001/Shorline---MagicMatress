/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import shoreline.BE.JSonObject;
import shoreline.BE.PlanningBE;
import shoreline.BLL.ShoreLineBLL;
import shoreline.DAL.ExcellAL;

/**
 * FXML Controller class
 *
 * @author atilk
 */
public class NewPatternWindowController implements Initializable {


    public String path;
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
    private ObservableList <PlanningBE> listPlanning =  FXCollections.observableArrayList();
    
    private ObservableList <String> listOfJasons = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
    }
    
    @FXML
    private void handleCancel(ActionEvent event) throws IOException 
    {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent Root = FXMLLoader.load(getClass().getResource("LoggedInWindow.fxml"));
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
        this.path = selectedFile.getAbsolutePath();
        PathField.setText(path); 
        System.out.println(path);
        makeComboBox(path);
        
    }

    @FXML
    private void buttonConvertAction(ActionEvent event) throws InvalidFormatException, Exception {
        
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
    
    
    
    private void makeComboBox(String path) throws Exception{
        clearComboBox();
        ShoreLineBLL bll = new ShoreLineBLL();
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
    
    
    public void Read(List list) throws IOException, InvalidFormatException, Exception{
        
        
        
        Workbook workbook = WorkbookFactory.create(new File(path)); //finds file
        Sheet sheet = workbook.getSheetAt(0);  // gets sheet
        DataFormatter dataFormatter = new DataFormatter();  // formats data
        
        ShoreLineBLL bll = new ShoreLineBLL();
        
        listData = bll.read(list);
        
        int rowCounter =0;
        int cellCounter = 0;
        
        
        
        
        for (Row row: sheet) {
            JSonObject jo = new JSonObject();
            PlanningBE planning = new PlanningBE();
            
            if (rowCounter !=0){
             for(Cell cell: row) {
                
                if(list.contains(cellCounter)){
                   
                    String cellValue = dataFormatter.formatCellValue(cell);
            
            
                    if( cellCounter == (int) list.get(0)){
                    jo.setAssetSerialNumber(cellValue);
                    }
                    if( cellCounter == (int) list.get(1)){
                    jo.setType(cellValue);
                    }
                    if( cellCounter == (int) list.get(2)){
                    jo.setExternalWorkOrderId(cellValue);
                    }
                    if( cellCounter == (int) list.get(3)){
                    jo.setSystemStatus(cellValue);
                    }
                    if( cellCounter == (int) list.get(4)){
                    jo.setUserStatus(cellValue);
                    }
                    
                    // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    Date date = new Date();
                    // dateFormat.format(date)
                     jo.setCreatedOn(date);
                    
                    if( cellCounter == (int) list.get(5)){
                    jo.setCreatedBy(cellValue);
                    }
                    if( cellCounter == (int) list.get(6)){
                    jo.setName(cellValue);
                    }
                    if( cellCounter == (int) list.get(7)){
                    jo.setPriority(cellValue);
                    }
                    
                    if( cellCounter == (int) list.get(8)){
                    jo.setStatus(cellValue);
                    }
                    
                    
                    // Planning BE
                    if( cellCounter == (int) list.get(9)){
                   
                    planning.setLastestFinishDate(cellValue);
                   
                    }
                    if( cellCounter == (int) list.get(10)){
                     
                    planning.setEarliestStartDate(cellValue);
                   
                    }
                    if( cellCounter == (int) list.get(11)){
                    
                    planning.setLatestStartDate(cellValue);
                   
                    }
                    if( cellCounter == (int) list.get(12)){
                    
                    planning.setEstimatedTime(cellValue);
                    
                    }
                    
                    jo.setPlanning(planning);
                    
                    
                }
                cellCounter++;
                
            }
            listPlanning.add(planning);
            listData.add(jo);
            cellCounter=0;
            }
            rowCounter++;
        }
        try (FileWriter file = new FileWriter("testfile.json")) {
            
            for (JSonObject jSonObject : listData) {

               Gson gson = new GsonBuilder().setPrettyPrinting().create();
               String json = gson.toJson(jSonObject);
             
               
                    
                
               file.write(json);
               file.flush();
               


                System.out.println( json);
                listOfJasons.add(json);
                
            } 
        }
        
    }


    
    
    
    
    
    
}
