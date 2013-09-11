package com.htm;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
    public void showToast(Context context, String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);
        Toast t = Toast.makeText(context, message, Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER_VERTICAL|Gravity.TOP, 0,0);
        t.setView(layout);
        t.show();
    }
}