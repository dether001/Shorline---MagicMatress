/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.GUI.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.annotation.PostConstruct;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import shoreline.BE.JSonObject;
import shoreline.BE.Tasks;
import shoreline.BE.User;
import shoreline.BLL.ShoreLineBLL;

/**
 * FXML Controller class
 *
 * @author atilk
 */
public class LoggedInWindowController implements Initializable {

    @FXML
    private Button LogOutBtn;
    @FXML
    private Button ViewLogBtn;
    @FXML
    private Label loggedInlbl;
    @FXML
    private Label dateLbl;
    @FXML
    private Button SavedPatternBtn;
    @FXML
    private Button NewPatternBtn;
    
    public String LoggedInUser;
    public int SelectedCompany;
    
    User user;
    LoginWindowController LogInWin;
    @FXML
    private TableView<Tasks> taskTable;
    @FXML
    private TableColumn<?, ?> patternClm;
    private PreparedStatement pst;
    private ResultSet rs;
    private Connection con;
    private String taskPath;
    private String taskPattern;
    private ObservableList<Tasks> taskList;
    @FXML
    private TableColumn<?, ?> pathClm;
    @FXML
    private Button taskRepeate;
    public String loadName;
    
    Model model = new Model();
    
    List<Integer> listOfIDs = new ArrayList<Integer>();
    private ObservableList <JSonObject> listData =  FXCollections.observableArrayList();    
    ShoreLineBLL bll = new ShoreLineBLL();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setCellTable();
    }

    
    //Event Handlers.
    @FXML
    private void handleCancel(ActionEvent event) throws IOException 
    {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent Root = FXMLLoader.load(getClass().getResource("/shoreline/GUI/View/SelectCompany.fxml"));
        Scene scene = new Scene (Root);
        stage.setScene(scene);
        stage.setTitle("ShoreLine - Data Converter");
        stage.show();
    }
    @FXML
    private void handleNewPattern (ActionEvent event) throws IOException 
    {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/shoreline/GUI/View/NewPatternWindow.fxml"));
            Parent root = (Parent) loader.load();
            
            NewPatternWindowController lwController = loader.getController();
            lwController.setUser(user);
            System.out.println(user.getName());
            Stage stage2 = new Stage();
            stage2.setScene(new Scene(root));
            stage2.show();
    }

    @FXML
    private void handleExistingPattern (ActionEvent event) throws IOException 
    {
        
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/shoreline/GUI/View/AddSPWindow.fxml"));
            Parent root = (Parent) loader.load();
            
            AddSPWindowController lwController = loader.getController();
            lwController.setUser(user);
            System.out.println(user.getName());
            Stage stage2 = new Stage();
            stage2.setScene(new Scene(root));
            stage2.show();
    }
    
    public void loadDataFromDB() {
        con = dba.DBConnection.Shoreline();
        List<Tasks> taskLists = new ArrayList<Tasks>();
        try {
            pst = con.prepareStatement("Select usedPattern, path FROM tasks WHERE created_by = ?");
            pst.setString(1, user.getName());
            rs = pst.executeQuery();
            while (rs.next()) {
                taskLists.add(new Tasks(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoggedInWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        taskTable.getItems().addAll(taskLists);
    }
    
    private void setCellTable() {
        patternClm.setCellValueFactory(new PropertyValueFactory<>("usedPattern"));
        pathClm.setCellValueFactory(new PropertyValueFactory<>("path"));
    }
    
    @FXML
    private void handleLog (ActionEvent event) throws IOException
    {
       if(user.getSelectedCompany() == 1)

       {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/shoreline/GUI/View/SLLogWindow.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setTitle("ShoreLine - Data Converter");
        stage.show();
       }
       else
       {
        Alert alert = new Alert(Alert.AlertType.NONE,"Feature is only available for ShoreLine users!",ButtonType.OK);
            alert.setTitle("Not a Shoreline User!");
            alert.showAndWait();   

       }
    }
    
    public void setUser (User user)
    {
        this.user = user;
        loadName = user.getName();
        loggedInlbl.setText(user.getName());
                  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    Date date = new Date();
                    dateLbl.setText(dateFormat.format(date)+"");
        loadDataFromDB();
        setCellTable();
    }
    public void setCompany (int id)
    {
        this.SelectedCompany = id;
    }

    @FXML
    private void handleTaskRepeate(ActionEvent event) {
        
            taskPattern = taskTable.getSelectionModel().getSelectedItem().getUsedPattern();
            taskPath = taskTable.getSelectionModel().getSelectedItem().getPath();
            
        try {

            
            getItemsID();
            Read(listOfIDs);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(LoggedInWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LoggedInWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        private void getItemsID(){
    
        String selectedPattern = taskPattern;
        listOfIDs = bll.getExistingPattern(selectedPattern);
    }
        
    public void Read(List list) throws IOException, InvalidFormatException, Exception{

        
        ShoreLineBLL bll = new ShoreLineBLL();
        
        listData = bll.read(list, taskPath);
        model.convert(listData);
    }

}
