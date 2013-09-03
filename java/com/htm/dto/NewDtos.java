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
}
