package com.htm.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by yanga on 2013/08/16.
 */
public class NewDtos implements Serializable {
    private Map<String,List<NewDto>> newEquiments;
    private Map<Integer,List<Repairs>> repairsPerNewEquipment;
    private Map<Integer,List<Stocks>> stockPerNewEquipment;
    public NewDtos(){
        newEquiments = new TreeMap<String, List<NewDto>>();
    }
    public Map<String,List<NewDto>> getNewEquiments(){
        return newEquiments;
    }
    public void setNewEquiments(Map<String,List<NewDto>> newEquiments){
        this.newEquiments = newEquiments;
    }
    public Map<Integer,List<Repairs>> getRepairsPerNewEquipment(){
        return repairsPerNewEquipment;
    }
    public void setRepairsPerNewEquipment(Map<Integer,List<Repairs>> repairsPerNewEquipment){
        this.repairsPerNewEquipment = repairsPerNewEquipment;
    }
    public Map<Integer,List<Stocks>> getStockPerNewEquipment(){
        return stockPerNewEquipment;
    }
    public void setStockPerNewEquipment(Map<Integer,List<Stocks>> stockPerNewEquipment){
        this.stockPerNewEquipment = stockPerNewEquipment;
    }
    public static class NewDto implements Serializable {
        private static final long serialVersionUID = -3999738557481545710L;
        private String device;
        private String make;
        private String hospital;
        private String location;
        private String tradeworld;
        private String serialNo;
        private String model;
        private String supplier;
        private int requisitionNumber;
        private String part_number;
        private String barcode;

        private String Rdate;
        private String Idate;
        private String Cdate;
        private String Wdate;
        private String QAdate;
        private String Sdate;

        public NewDto(String hospital,String location,String tradeworld,
                      String Rdate, String Idate, String Cdate, String Wdate, String QAdate, String Sdate,
                      String serialNo, String device, String make, String model){
            this.serialNo = serialNo;
            this.device = device;
            this.make = make;
            this.model = model;
            this.hospital = hospital;
            this.location = location;
            this.tradeworld = tradeworld;
            this.Rdate = Rdate;
            this.Idate = Idate;
            this.Cdate = Cdate;
            this.Wdate = Wdate;
            this.QAdate = QAdate;
            this.Sdate = Sdate;
        }
        public String getSerialNumber(){
            return serialNo;
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

        public String getHospital() {
            return hospital;
        }

        public String getLocation() {
            return location;
        }

        public String getTradeworld() {
            return tradeworld;
        }

        public String getRdate() {
            return Rdate;
        }

        public String getIdate() {
            return Idate;
        }

        public String getCdate() {
            return Cdate;
        }

        public String getWdate() {
            return Wdate;
        }

        public String getQAdate() {
            return QAdate;
        }

        public String getSdate() {
            return Sdate;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setRequisitionNumber(int requisitionNumber){
            this.requisitionNumber = requisitionNumber;
        }
        public void setPart_number(String part_number){
            this.part_number = part_number;
        }
    }
}
