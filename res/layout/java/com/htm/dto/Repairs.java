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
}
