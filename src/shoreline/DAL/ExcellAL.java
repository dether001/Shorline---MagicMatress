/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.DAL;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import shoreline.BE.JSonObject;
import shoreline.BE.PlanningBE;

/**
 *
 * @author Arman
 */
public class ExcellAL {
    
    
    public String setOutputFile() throws Exception{
       
       String xlsxFilePath = "./.xlsx";
        return "./Import_data.xlsx";
    }

    public List<String> makeComboboxes(String path) throws IOException, InvalidFormatException {
        
        Workbook workbook = WorkbookFactory.create(new File(path));
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        List<String> rowList = new ArrayList();
        
        for (Cell cell : sheet.getRow(0)) {
                String cellValue = dataFormatter.formatCellValue(cell);
                rowList.add(cellValue);

        }
        
        return rowList;
    }

    public ObservableList<JSonObject> read(List list) {
        
        
        return null;
            
    }

    public ObservableList<JSonObject> read(List list, String path) throws IOException, InvalidFormatException {
        
        Workbook workbook = WorkbookFactory.create(new File(path)); //finds file
        Sheet sheet = workbook.getSheetAt(0);  // gets sheet
        DataFormatter dataFormatter = new DataFormatter();  // formats data
        
        int rowCounter =0;
        int cellCounter = 0;
        
         ObservableList <JSonObject> listData =  FXCollections.observableArrayList();
        
        
        
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
            listData.add(jo);
            cellCounter=0;
            }
            rowCounter++;
        }
        
        return listData;
        
    }
    
}
