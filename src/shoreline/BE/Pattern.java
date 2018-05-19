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
public class Pattern {
    
    private String created_By_User;
    private int assestSeriliaNum;
    private int type;
    private int ExternalWorkOrder;
    private int systemStatus;
    private int userStatus;
    private int createdBy;
    private int name;
    private int Priority;
    private int status;
    private int latestFinishDate;
    private int earliestStartDate;
    private int latestStartDate;
    private int estimatedTime;
    private String patternName;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getPatternName() {
        return patternName;
    }

    public void setPatternName(String patternName) {
        this.patternName = patternName;
    }


    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }


    public int getLatestStartDate() {
        return latestStartDate;
    }

    public void setLatestStartDate(int latestStartDate) {
        this.latestStartDate = latestStartDate;
    }


    public int getEarliestStartDate() {
        return earliestStartDate;
    }

    public void setEarliestStartDate(int earliestStartDate) {
        this.earliestStartDate = earliestStartDate;
    }


    public int getLatestFinishDate() {
        return latestFinishDate;
    }

    public void setLatestFinishDate(int latestFinishDate) {
        this.latestFinishDate = latestFinishDate;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int getPriority() {
        return Priority;
    }

    public void setPriority(int Priority) {
        this.Priority = Priority;
    }


    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }


    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }


    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }


    public int getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(int systemStatus) {
        this.systemStatus = systemStatus;
    }


    public int getExternalWorkOrder() {
        return ExternalWorkOrder;
    }

    public void setExternalWorkOrder(int ExternalWorkOrder) {
        this.ExternalWorkOrder = ExternalWorkOrder;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public int getAssestSeriliaNum() {
        return assestSeriliaNum;
    }

    public void setAssestSeriliaNum(int assestSeriliaNum) {
        this.assestSeriliaNum = assestSeriliaNum;
    }


    public String getCreatedBy_User() {
        return created_By_User;
    }

    public void setCreatedBy_User(String created_By_User) {
        this.created_By_User = created_By_User;
    }

    
}
