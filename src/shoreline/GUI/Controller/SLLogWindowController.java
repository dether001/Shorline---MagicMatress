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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import shoreline.BE.Log;
import shoreline.BE.User;
import shoreline.BLL.ShoreLineBLL;

/**
 * FXML Controller class
 *
 * @author atilk
 */
public class SLLogWindowController implements Initializable {

    @FXML
    private TableColumn<?, ?> userClm;
    @FXML
    private TableColumn<?, ?> actionClm;
    @FXML
    private TableColumn<?, ?> dateClm;
    @FXML
    private ComboBox<String> companyCombo;
    @FXML
    private Label loggedInlbl;
    @FXML
    private Label dateLbl;
    @FXML
    private Button cancelBtn;
    @FXML
    private TableView<Log> logTable;
    private PreparedStatement pst;
    private ResultSet rs;
    private Connection con;
    public int companyCID;
    public String companyName;
    private ObservableList<Log> logList;
    User user;
    static int cLoad;
    ShoreLineBLL bll = new ShoreLineBLL();
    private static Logger loggerErrorSaver =getLogger(SLLogWindowController.class);


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        con = dba.DBConnection.Shoreline();
        System.out.println(cLoad);
        logList = FXCollections.observableArrayList();
    }
    
    @FXML
    private void handleCancel(ActionEvent event)
    {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }    
    
    private void setCellTable() {
        userClm.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        actionClm.setCellValueFactory(new PropertyValueFactory<>("action"));
        dateClm.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
    
    private void loadDataFromDB() {
        
        try {
            pst = con.prepareStatement("Select user_name, action, date FROM actionlog WHERE cid = ?");
            pst.setInt(1, companyCID);
            rs = pst.executeQuery();
            while(rs.next()) {
                logList.add(new Log(rs.getString(1), rs.getString(2), ""+rs.getDate(3)));
            }
        } catch (SQLException ex) {
            loggerErrorSaver.error("error while loadingDataFromDB. SLLogWindow: " + ex + ex);
            bll.taskException(user);
        }
        logTable.setItems(logList);
        
    }
    
    @FXML
    private void selectCID(ActionEvent event) {
        logList.clear();
        companyName = companyCombo.getSelectionModel().getSelectedItem();
        if ("Shoreline".equals(companyName)) {
            companyCID = 1;
        }
        else if ("ECompany".equals(companyName)) {
            companyCID = 2;
        }
        
        loadDataFromDB();
        setCellTable();
        
    }
        // setUser method is used all around our program as a "pre-initalization" method
        public void setUser (User user)
    {
        this.user = user;
        System.out.println("setuser: " + user.getSelectedCompany());
        switch (user.getSelectedCompany()) {
            case 1: companyCombo.getItems().addAll("Shoreline", "ECompany"); break;
            case 2: companyCombo.getItems().addAll("ECompany"); break;
        }
        loggedInlbl.setText("Logged-in as: " + user.getName());
                  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    Date date = new Date();
                    dateLbl.setText("Current Date: " + dateFormat.format(date));
    }
    
    
}
