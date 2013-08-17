package com.htm;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.htm.biz.LogonManager;
import com.htm.biz.NewDtoManager;
import com.htm.biz.RepairsRequisitionManager;
import com.htm.biz.StockManager;

/**
 * Created by yanga on 2013/08/13.
 */
public class BaseActivity extends FragmentActivity {
    private static final String TAG = BaseActivity.class.getName();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    CeECOApplication getCeECOApplication(){
        return (CeECOApplication)this.getApplication();
    }
    LogonManager getLogonManager(){
        return getCeECOApplication().getLogonManager();
    }
    public StockManager getStockManager(){
        return getCeECOApplication().getStockManager();
    }
    public NewDtoManager getNewEquipmentManager(){
        return getCeECOApplication().getNewEquipmentManager();
    }
    public RepairsRequisitionManager getRepairsRequisitionManager(){
        return getCeECOApplication().getRepairsRequisitionManager();
    }
}