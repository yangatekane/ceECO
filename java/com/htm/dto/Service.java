package com.htm.dto;

import java.io.Serializable;

/**
 * Created by yanga on 2013/09/29.
 */
public class Service implements Serializable {
    private String serviceDate;
    private String serialNo;
    private String make;
    private String model;
    private String hospital;
    private String barcode;

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

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Service(String hospital, String model, String make, String serviceDate, String serialNo) {
        this.hospital = hospital;
        this.model = model;
        this.make = make;
        this.serviceDate = serviceDate;
        this.serialNo = serialNo;
    }
    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }
}
