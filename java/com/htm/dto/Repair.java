package com.htm.dto;

import java.io.Serializable;

/**
 * Created by yanga on 2013/08/17.
 */
public class Repair implements Serializable{
    private int requisitionNumber;
    private String tel;
    private String date;
    private String section;
    private String floor;
    private String description;
    private String department;
    private String reportedBy;
    private String receivedBy;
    public Repair(int requisitionNumber, String tel, String date, String section,
                 String department,String floor,String description,
                 String reportedBy,String receivedBy){
                 this.requisitionNumber = requisitionNumber;
                 this.date = date;
                 this.tel = tel;
                 this.section = section;
                 this.department = department;
                 this.floor = floor;
                 this.description = description;
                 this.reportedBy = reportedBy;
                 this.receivedBy = receivedBy;
    }
    public int getRequisitionNumber(){
        return requisitionNumber;
    }
    public String getTel(){
        return tel;
    }
    public String getDate(){
        return date;
    }
    public String getSection(){
        return section;
    }
    public String getFloor(){
        return floor;
    }
    public String getDescription(){
        return description;
    }
    public String getDepartment(){
        return department;
    }
    public String getReportedBy(){
        return reportedBy;
    }
    public String getReceivedBy(){
        return receivedBy;
    }
}
