package com.htm.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by yanga on 2013/08/14.
 */
public class Stocks implements Serializable {
    private Map<String,List<Stock>> stocks;
    public Stocks(){
        stocks = new TreeMap<String,List<Stock>>();
    }
    public Map<String,List<Stock>> getStocks(){
        return stocks;
    }
    public void setStocks(Map<String,List<Stock>> stocks){
        this.stocks = stocks;
    }

    public static class Stock implements Serializable {
        private static final long serialVersionUID = -3999738557481545710L;
        private String part_number;
        private String description;
        private int quantity;
        private String amount;
        private String barcode;
        public Stock(String part_number, String description, int quantity, String amount){
            this.part_number = part_number;
            this.description = description;
            this.quantity = quantity;
            this.amount = amount;
        }
        public String getPartNumber(){
            return part_number;
        }
        public String getDescription(){
            return description;
        }
        public int getQuantity(){
            return quantity;
        }
        public String getAmount(){
            return amount;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }
    }

}
