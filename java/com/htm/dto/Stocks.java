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
}
