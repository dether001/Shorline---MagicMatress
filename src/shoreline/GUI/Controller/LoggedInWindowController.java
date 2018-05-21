/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.GUI.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import shoreline.BE.Tasks;
import shoreline.BE.User;

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
    private ObservableList<Tasks> taskList;
    @FXML
    private TableColumn<?, ?> pathClm;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        con = dba.DBConnection.Shoreline();
        taskList = FXCollections.observableArrayList();
        loadDataFromDB();
        setCellTable();
    }
    
     public void setMainViewCont(LoginWindowController LogInWin) {
        this.LogInWin=LogInWin;
        user = LogInWin.returnUser();
        
        
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
            lwController.setUser(LoggedInUser);
            Stage stage2 = new Stage();
            stage2.setScene(new Scene(root));
            stage2.show();
    }

    @FXML
    private void handleExistingPattern (ActionEvent event) throws IOException 
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/shoreline/GUI/View/AddSPWindow.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setTitle("ShoreLine - Data Converter");
        stage.show();
    }
    
    private void loadDataFromDB() {
        try {
            pst = con.prepareStatement("Select usedPattern, path FROM tasks");
            rs = pst.executeQuery();
            while (rs.next()) {
                taskList.add(new Tasks(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoggedInWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        taskTable.setItems(taskList);
    }
    
    private void setCellTable() {
        patternClm.setCellValueFactory(new PropertyValueFactory<>("usedPattern"));
        pathClm.setCellValueFactory(new PropertyValueFactory<>("path"));
    }
    
    @FXML
    private void handleLog (ActionEvent event) throws IOException
    {
       if(SelectedCompany == 1)

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

    public User returnUser(){
    
    return user;
    }
    
    public void setUser (String user)
    {
        this.LoggedInUser = user;
    }
    public void setCompany (int id)
    {
        this.SelectedCompany = id;
    }
}
