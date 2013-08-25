package com.htm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

/**
 * Created by yanga on 2013/08/15.
 */
public class NewEquipmentActivity extends BaseActivity {
    private static final String TAG=NewEquipmentActivity.class.getName();
    public AlertDialog newSaved;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_equipment);
        final EditText serialNumber = (EditText)findViewById(R.id.edit_serial_number);
        final EditText device = (EditText)findViewById(R.id.edit_device);
        final EditText make = (EditText)findViewById(R.id.edit_make);
        final EditText model = (EditText)findViewById(R.id.edit_model);
        final EditText supplier = (EditText)findViewById(R.id.edit_supplier);
        newSaved = new AlertDialog.Builder(this)
                .setMessage("New Equipment information saved....")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        serialNumber.setText("");
                        device.setText("");
                        make.setText("");
                        model.setText("");
                        supplier.setText("");
                        newSaved.dismiss();
                    }
              }).create();
            ((Button)findViewById(R.id.btn_save_new_equipment)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (serialNumber.getText().toString().equalsIgnoreCase("")||
                                device.getText().toString().equalsIgnoreCase("")||
                                make.getText().toString().equalsIgnoreCase("")||
                                model.getText().toString().equalsIgnoreCase("")||
                                supplier.getText().toString().equalsIgnoreCase("")){
                            newSaved.setMessage("Please fill in all fields");
                            newSaved.show();
                        }else {
                            getNewEquipmentManager().saveNewEquipment("recievedEquipment","commissioned",
                                    serialNumber.getText().toString(),
                                    device.getText().toString(),
                                    make.getText().toString(),
                                    model.getText().toString(),
                                    supplier.getText().toString());
                                    newSaved.setMessage("New Equipment information saved....");
                                    newSaved.show();
                        }
                    } catch (IOException e) {
                        newSaved.dismiss();
                        Log.e(TAG,e.getMessage(),e);
                    }
                }
            });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                break;
            case R.id.home_action_new_report:
                startActivity(new Intent(NewEquipmentActivity.this,NewEquipmentReportActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}