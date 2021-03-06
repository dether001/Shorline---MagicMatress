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
import java.util.Set;
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
import org.apache.log4j.Logger;
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
    @FXML
    private TableView<Tasks> taskTable;
    @FXML
    private TableColumn<?, ?> patternClm;
    @FXML
    private TableColumn<?, ?> pathClm;
    @FXML
    private Button taskRepeate;
    
    public String LoggedInUser;
    public int SelectedCompany;
    
    User user;
    LoginWindowController LogInWin;
    
    private PreparedStatement pst;
    private ResultSet rs;
    private Connection con;
    private String taskPath;
    private String taskPattern;
    public ObservableList<Tasks> taskList;
    

    public String loadName;
    
    Model model = new Model();
    
    List<Integer> listOfIDs = new ArrayList<Integer>();
    private ObservableList <JSonObject> listData =  FXCollections.observableArrayList();    
    ShoreLineBLL bll = new ShoreLineBLL();
    
    private static Logger loggerErrorSaver = Logger.getLogger(NewPatternWindowController.class);
    @FXML
    private Button refresh;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        taskList = FXCollections.observableArrayList();
    }

    
    //Event Handlers.
    @FXML
    private void handleCancel(ActionEvent event)
    {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        createScene("cancel");
    }
    @FXML
    private void handleNewPattern (ActionEvent event) 
    {
        createScene("NewPatternWindow");
    }
    @FXML
    private void refresh (ActionEvent event) {
        taskList.clear();
        taskTable.getItems().clear();
        loadDataFromDB();
        setCellTable();
    }
    
    @FXML
    private void handleExistingPattern (ActionEvent event) throws IOException
    {
        createScene("AddSPWindow");
    }
    
    @FXML
    private void handleLog (ActionEvent event) throws IOException
    {
        createScene("SLLogWindow");
    }

    private void getItemsID()
    {
        String selectedPattern = taskPattern;
        listOfIDs = bll.getExistingPattern(selectedPattern);
    }
    
    private void createScene (String scene)
    {

        if(scene == "AddSPWindow")
        {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/shoreline/GUI/View/"+scene+".fxml"));
                Parent root = (Parent) loader.load();
                
                AddSPWindowController ctrl = loader.getController();
                ctrl.setUser(user);
                
                Stage stage = new Stage();
                stage.setTitle("ShoreLine - Data Converter");
                stage.setScene(new Scene(root));
                stage.setResizable(false); 
                stage.show();
            } catch (IOException ex) {
               loggerErrorSaver.error("error while Clicking Handle Existing Pattern in LoggedInWindow: " + ex + ex);
            bll.taskException(user);
            }
        }
        if(scene == "NewPatternWindow")
        {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/shoreline/GUI/View/NewPatternWindow.fxml"));
                Parent root = (Parent) loader.load();
                
                NewPatternWindowController ctrl = loader.getController();
                ctrl.setUser(user);
                
                Stage stage = new Stage();
                stage.setTitle("ShoreLine - Data Converter");
                stage.setScene(new Scene(root));
                stage.setResizable(false);   
                stage.show();
            } catch (IOException ex) {
               loggerErrorSaver.error("error while Clicking Handle Existing Pattern in LoggedInWindow: " + ex + ex);
            bll.taskException(user);
            }
        }
        if(scene == "SLLogWindow")
        {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/shoreline/GUI/View/"+scene+".fxml"));
                Parent root1 = (Parent) loader.load();
                
                SLLogWindowController ctrl = loader.getController();
                ctrl.setUser(user);
                
                Stage stage = new Stage();
                stage.setTitle("ShoreLine - Data Converter");
                stage.setScene(new Scene(root1));
                stage.setResizable(true); 
                stage.show();
            } catch (IOException ex) {
                loggerErrorSaver.error("error while Clicking Handle Existing Pattern in LoggedInWindow: " + ex + ex);
            bll.taskException(user);
            }
        }
        if (scene == "cancel")
        {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/shoreline/GUI/View/LoginWindow.fxml"));
                Parent root1 = (Parent) loader.load();
                Stage stage = new Stage();
                stage.setTitle("ShoreLine - Data Converter");
                stage.setScene(new Scene(root1));
                stage.setResizable(true);
                stage.show();
            } catch (IOException ex) {
               loggerErrorSaver.error("error while Clicking Handle Existing Pattern in LoggedInWindow: " + ex + ex);
            bll.taskException(user);
            }
        }
    }
    
    //Quieries the DB for all tasks that was created by the logged in user, then loads it into an Observable list, then the list to the TableView
    public void loadDataFromDB() {
        if (user.getSelectedCompany() == 1) {
            con = dba.DBConnection.Shoreline();
        }
        else if (user.getSelectedCompany() == 2) {
            con = dba.DBConnection.ECompany();
        }
        taskList.clear();
        try {
            pst = con.prepareStatement("Select usedPattern, path FROM tasks WHERE created_by = ?");
            pst.setString(1, user.getName());
            rs = pst.executeQuery();
            while (rs.next()) {
                taskList.add(new Tasks(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            loggerErrorSaver.error("error while trying loadingUsedPatternsFromDataBase 'Created_by': " + ex + ex);
            bll.taskException(user);
        }
        taskTable.getItems().addAll(taskList);
    }
    
    //Sets the cells for the tablieview
    private void setCellTable() {
        patternClm.setCellValueFactory(new PropertyValueFactory<>("usedPattern"));
        pathClm.setCellValueFactory(new PropertyValueFactory<>("path"));
    }
    
    // setUser method is used all around our program as a "pre-initalization" method
    public void setUser (User user)
    {
        this.user = user;
        loadName = user.getName();
        loggedInlbl.setText("Logged-in as: " + user.getName());
                  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    Date date = new Date();
                    dateLbl.setText("Current Date: " + dateFormat.format(date));
        loadDataFromDB();
        setCellTable();
    }
    
    public void setCompany (int id)
    {
        this.SelectedCompany = id;
    }
   
    @FXML
    private void handleTaskRepeate(ActionEvent event) 
    {
        startTask("convert");
    }
        
    public void Read(List list) 
    {
        try 
        {
            ShoreLineBLL bll = new ShoreLineBLL();
            listData = bll.read(list, taskPath);
            model.convert(listData);
        } 
        catch (IOException ex) 
        {
             loggerErrorSaver.error("error while trying repeat task from Table view with read: " + ex + ex);
             
             bll.taskException(user);
        } 
    }
    
    public void startTask(String string)
    {
    if(string=="convert")
        {
            Runnable task = new Runnable()
            {
                @Override
                public void run() 
                {
                    try 
                    {
                        try 
                        {
                            taskPattern = taskTable.getSelectionModel().getSelectedItem().getUsedPattern();
                            taskPath = taskTable.getSelectionModel().getSelectedItem().getPath();
                            getItemsID();
                            Read(listOfIDs);
                        } 
                        catch (Exception ex) 
                        {
                            loggerErrorSaver.error("error while trying repeat task from Table view with read:': " + ex + ex);
                            bll.taskException(user);
                        }
                    } 
                    catch (Exception ex) 
                    {
                        loggerErrorSaver.error("error while converting: " + ex + ex);
                        bll.taskException(user);
                    }
                }   
            };
            Thread ConvThread = new Thread(task);
            ConvThread.setDaemon(true);
            ConvThread.start();
        }
    }

}
