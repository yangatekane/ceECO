package com.htm.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yanga on 2013/09/22.
 */
public class Parts implements Serializable {
    private List<Part> parts;
    public Parts(){
        parts = new LinkedList<Part>();
    }
    public List<Part> getParts(){
        return parts;
    }
    public void setParts(List<Part> parts){
        this.parts = parts;
    }

    public static class Part implements Serializable {
        private static final long serialVersionUID = -3999738557481545710L;
        private String part_number;
        private String description;
        private int quantity;
        private String amount;
        private String barcode;
        private String supplier;
        private String date;

        public static long getSerialVersionUID() {
            return serialVersionUID;
        }

        public String getPart_number() {
            return part_number;
        }

        public void setPart_number(String part_number) {
            this.part_number = part_number;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void setAmount(String amount) {
            this.amount = amount;
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

        public Part(String part_number, String description, int quantity, String amount, String barcode, String supplier, String date) {
            this.part_number = part_number;
            this.description = description;
            this.quantity = quantity;
            this.amount = amount;
            this.barcode = barcode;
            this.supplier = supplier;
            this.date = date;
        }

        public Part(String part_number, String description, int quantity, String amount, String barcode, String supplier) {
            this.part_number = part_number;
            this.description = description;
            this.quantity = quantity;
            this.amount = amount;
            this.barcode = barcode;
            this.supplier = supplier;
        }

        public Part(String part_number, String description, int quantity, String amount){
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
