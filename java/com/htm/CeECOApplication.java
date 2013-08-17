package com.htm;

import android.app.Application;

import com.htm.biz.LogonManager;
import com.htm.biz.NewDtoManager;
import com.htm.biz.StockManager;

/**
 * Created by yanga on 2013/08/13.
 */
public class CeECOApplication extends Application {
    private static final String TAG = CeECOApplication.class.getName();
    private LogonManager logonManager;
    private StockManager stockManager;
    private NewDtoManager newEquipmentManager;
    @Override
    public void onCreate() {
        super.onCreate();
        logonManager = new LogonManager(this);
        stockManager = new StockManager(this);
        newEquipmentManager = new NewDtoManager(this);
    }
    LogonManager getLogonManager(){
        return logonManager;
    }
    StockManager getStockManager(){
        return stockManager;
    }
    NewDtoManager getNewEquipmentManager(){
        return newEquipmentManager;
    }
}
