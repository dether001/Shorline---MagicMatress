/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.BE;

import java.util.Date;

/**
 *
 * @author Arman
 */
public class JSonObject {
    
    private String assetSerialNumber;
    private String type;
    private String externalWorkOrderId;
    private String systemStatus;
    private String userStatus;
    private Date createdOn;
    private String createdBy;
    private String name;
    private String priority;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    
    

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }


    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }


    public String getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(String systemStatus) {
        this.systemStatus = systemStatus;
    }


    public String getExternalWorkOrderId() {
        return externalWorkOrderId;
    }

    public void setExternalWorkOrderId(String externalWorkOrderId) {
        this.externalWorkOrderId = externalWorkOrderId;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getAssetSerialNumber() {
        return assetSerialNumber;
    }

    public void setAssetSerialNumber(String assetSerialNumber) {
        this.assetSerialNumber = assetSerialNumber;
    }
    
    

    
}
