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
    public NewDtos(){
        newEquiments = new TreeMap<String, List<NewDto>>();
    }
    public Map<String,List<NewDto>> getNewEquiments(){
        return newEquiments;
    }
    public void setNewEquiments(Map<String,List<NewDto>> newEquiments){
        this.newEquiments = newEquiments;
    }
}
