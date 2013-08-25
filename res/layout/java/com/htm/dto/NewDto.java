package com.htm.dto;

import java.io.Serializable;

/**
 * Created by yanga on 2013/08/16.
 */
public class NewDto implements Serializable {
    private static final long serialVersionUID = -3999738557481545710L;
    private String serial_number;
    private String device;
    private String make;
    private String model;
    private String supplier;
    public NewDto(String serial_number, String device, String make, String model, String supplier){
        this.serial_number = serial_number;
        this.device = device;
        this.make = make;
        this.model = model;
        this.supplier = supplier;
    }
    public String getSerialNumber(){
        return serial_number;
    }
    public String getDevice(){
        return device;
    }
    public String getMake(){
        return make;
    }
    public String getModel(){
        return model;
    }
    public String getSupplier(){
        return  supplier;
    }
}
