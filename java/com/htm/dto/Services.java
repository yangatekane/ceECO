package com.htm.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yanga on 2013/09/29.
 */
public class Services implements Serializable {
    private List<Service> services;

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
