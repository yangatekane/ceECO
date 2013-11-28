package com.htm.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by yanga on 2013/09/17.
 */
public class Jobs implements Serializable {
    private Map<String,List<Job>> jobs;

    public Map<String, List<Job>> getJobs() {
        return jobs;
    }

    public Jobs() {
        jobs = new TreeMap<String, List<Job>>();
    }

    public void setJobs(Map<String, List<Job>> jobs) {
        this.jobs = jobs;
    }

    public static class Job implements Serializable{
        private String serialNo;
        private String make;
        private String model;

        public String getTechnician() {
            return technician;
        }

        public void setTechnician(String technician) {
            this.technician = technician;
        }

        private String technician;
        private String contactPerson;
        private String requisitionNo;
        private Parts parts;
        private String barcode;

        public Parts getParts() {
            return parts;
        }

        public void setParts(Parts parts) {
            this.parts = parts;
        }

        private String supplier;
        private String date;
        private String status;
        private String fault_description;

        public String getFault_description() {
            return fault_description;
        }

        public void setFault_description(String fault_description) {
            this.fault_description = fault_description;
        }

        public String getSerialNo() {
            return serialNo;
        }

        public void setSerialNo(String serialNo) {
            this.serialNo = serialNo;
        }

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getContactPerson() {
            return contactPerson;
        }

        public void setContactPerson(String contactPerson) {
            this.contactPerson = contactPerson;
        }

        public String getRequisitionNo() {
            return requisitionNo;
        }

        public void setRequisitionNo(String requisitionNo) {
            this.requisitionNo = requisitionNo;
        }

        public Job(Parts parts,String date, String status, String fault_description,
                   String serialNo,String make,String model,String technician,String contactPerson,String requisitionNo) {
            this.date = date;
            this.status = status;
            this.fault_description = fault_description;
            this.serialNo = serialNo;
            this.make = make;
            this.model = model;
            this.technician = technician;
            this.contactPerson = contactPerson;
            this.requisitionNo = requisitionNo;
            this.parts = parts;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getSupplier() {
            return supplier;
        }

        public void setSupplier(String supplier) {
            this.supplier = supplier;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
