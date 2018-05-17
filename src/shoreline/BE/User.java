/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.BE;

/**
 *
 * @author Arman
 */
public class User {
    
    private String name;
    private String password;
    private int id;
    private int selectedCompany;

    public int getSelectedCompany() {
        return selectedCompany;
    }

    public void setSelectedCompany(int selectedCompany) {
        this.selectedCompany = selectedCompany;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

        
        public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    
}
