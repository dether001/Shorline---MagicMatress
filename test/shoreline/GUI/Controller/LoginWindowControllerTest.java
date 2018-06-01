/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.GUI.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import shoreline.BE.User;

/**
 *
 * @author Arman
 */
public class LoginWindowControllerTest {
    
    public LoginWindowControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of initialize method, of class LoginWindowController.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        URL url = null;
        ResourceBundle rb = null;
        LoginWindowController instance = new LoginWindowController();
        instance.initialize(url, rb);
        
    }

   

    /**
     * Test of setCID method, of class LoginWindowController.
     */
    @Test
    public void testSetCID() {
        System.out.println("setCID");
        User user = new User();
        LoginWindowController instance = new LoginWindowController();
        instance.setCID(user);
        assertNotNull(user);
    }

    /**
     * Test of returnUser method, of class LoginWindowController.
     */
    @Test
    public void testReturnUser() {
        System.out.println("returnUser");
        LoginWindowController instance = new LoginWindowController();
        User expResult = new User();
        expResult.setId(-1);
        expResult.setName(null);
        expResult.setPassword(null);
        expResult.setSelectedCompany(1);
        User result = instance.returnUser();
        assertNotEquals(expResult, result);
       
    }

    /**
     * Test of setCompany method, of class LoginWindowController.
     */
    @Test
    public void testSetCompany() {
        System.out.println("setCompany");
        int id = 0;
        LoginWindowController instance = new LoginWindowController();
        instance.setCompany(id);
        
        
    }
    @Test
    public void testLogIn(){
    
    User user = new User();
    user.setName("name");
        user.setPassword("Password");
        user.setId(1);
        user.setSelectedCompany(1); 
        if (user.getId() !=-1){
        user = null;
        }
        
        assertNull(user);
    
    
    }
    
    /**
     *
     */
    @Test
   public void lowerTest() {
    User o1 = new User();
        assertNotNull(o1);
    
}
    
}