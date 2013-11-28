package com.htm.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by yanga on 2013/08/17.
 */
public class Repairs implements Serializable {
    private Map<String,List<Repair>> repairs;
    public Repairs(){
        repairs = new TreeMap<String, List<Repair>>();
    }
    public Map<String,List<Repair>> getRepairs(){
        return repairs;
    }
    public void setRepairs(Map<String,List<Repair>> repairs){
        this.repairs = repairs;
    }
    public static class Repair implements Serializable{
        private String serialNo;
        private String make;
        private String model;
        private int requisitionNumber;
        private String tel;
        private String date;
        private String section;
        private String floor;
        private String description;
        private String job_status;
        private String department;
        private String reportedBy;
        private String receivedBy;
        private String barcode;
        private String serviceDate;

        public String getServiceDate() {
            return serviceDate;
        }

        public void setServiceDate(String serviceDate) {
            this.serviceDate = serviceDate;
        }

        public String getJob_status() {
            return job_status;
        }

        public void setJob_status(String job_status) {
            this.job_status = job_status;
        }

        public Repair(String serialNo, String make, String model,
                      int requisitionNumber, String tel, String date, String section,
                      String department,String floor,String description,
                      String reportedBy,String receivedBy){
            this.serialNo = serialNo;
            this.make = make;
            this.model = model;
            this.requisitionNumber = requisitionNumber;
            this.date = date;
            this.tel = tel;
            this.section = section;
            this.department = department;
            this.floor = floor;
            this.description = description;
            this.reportedBy = reportedBy;
            this.receivedBy = receivedBy;
            this.job_status = "opened";
        }

        public String getSerialNo() {
            return serialNo;
        }

        public String getMake() {
            return make;
        }

        public String getModel() {
            return model;
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

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }
    }

}
