/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.BE;

/**
 *
 * @author Viktor
 */
public class Tasks {
    
    private String usedPattern;
    private String path;
    
    public Tasks (String usedPattern, String path) {
        this.usedPattern = usedPattern;
        this.path = path;
    }

    public String getUsedPattern() {
        return usedPattern;
    }

    public void setUsedPattern(String usedPattern) {
        this.usedPattern = usedPattern;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    

    
}
