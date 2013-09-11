package com.htm;

import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.htm.biz.LogonManager;
import com.htm.biz.NewDtoManager;
import com.htm.biz.RepairsRequisitionManager;
import com.htm.biz.StockManager;

/**
 * Created by yanga on 2013/08/13.
 */
public class CeECOApplication extends Application {
    private static final String TAG = CeECOApplication.class.getName();
    private LogonManager logonManager;
    private StockManager stockManager;
    private NewDtoManager newEquipmentManager;
    private RepairsRequisitionManager repairsRequisitionManager;
    @Override
    public void onCreate() {
        super.onCreate();
        logonManager = new LogonManager(this);
        stockManager = new StockManager(this);
        newEquipmentManager = new NewDtoManager(this);
        repairsRequisitionManager = new RepairsRequisitionManager(this);
    }
    public LogonManager getLogonManager(){
        return logonManager;
    }
    public StockManager getStockManager(){
        return stockManager;
    }
    public NewDtoManager getNewEquipmentManager(){
        return newEquipmentManager;
    }
    public RepairsRequisitionManager getRepairsRequisitionManager(){
        return repairsRequisitionManager;
    }

}
