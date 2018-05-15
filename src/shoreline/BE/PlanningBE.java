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
public class PlanningBE {
    
    private String lastestFinishDate;

    public String getLastestFinishDate() {
        return lastestFinishDate;
    }

    public void setLastestFinishDate(String lastestFinishDate) {
        this.lastestFinishDate = lastestFinishDate;
    }

    private String earliestStartDate;

    public String getEarliestStartDate() {
        return earliestStartDate;
    }

    public void setEarliestStartDate(String earliestStartDate) {
        this.earliestStartDate = earliestStartDate;
    }

    private String latestStartDate;

    public String getLatestStartDate() {
        return latestStartDate;
    }

    public void setLatestStartDate(String latestStartDate) {
        this.latestStartDate = latestStartDate;
    }
    
    private String estimatedTime;

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }


    
}
