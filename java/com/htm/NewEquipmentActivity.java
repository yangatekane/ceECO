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
import android.widget.Spinner;

import java.io.IOException;

/**
 * Created by yanga on 2013/08/15.
 */
public class NewEquipmentActivity extends BaseActivity {
    private static final String TAG=NewEquipmentActivity.class.getName();
    public AlertDialog newSaved;
    private EditText device;
    private EditText make;
    private EditText hospital;
    private EditText serialNumber;
    private EditText model;
    private EditText supplier;
    private EditText location;
    private EditText tradeworld;
    private EditText tenderNumber;
    private EditText orderNumber;
    private EditText cost;
    private Spinner Rday,Rmonth,Ryear;
    private Spinner Iday,Imonth,Iyear;
    private Spinner Cday,Cmonth,Cyear;
    private Spinner Wday,Wmonth,Wyear;
    private Spinner QAday,QAmonth,QAyear;
    private Spinner Sday,Smonth,Syear;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_equipment);
        setup();
        newSaved = new AlertDialog.Builder(this)
                .setMessage("New Equipment information saved....")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        destruct();
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
                            String Rdate = String.valueOf(Rday.getSelectedItem())+" "+String.valueOf(Rmonth.getSelectedItem())+" "+String.valueOf(Ryear.getSelectedItem());
                            String Idate = String.valueOf(Iday.getSelectedItem())+" "+String.valueOf(Imonth.getSelectedItem())+" "+String.valueOf(Iyear.getSelectedItem());
                            String Cdate = String.valueOf(Cday.getSelectedItem())+" "+String.valueOf(Cmonth.getSelectedItem())+" "+String.valueOf(Cyear.getSelectedItem());
                            String Wdate = String.valueOf(Wday.getSelectedItem())+" "+String.valueOf(Wmonth.getSelectedItem())+" "+String.valueOf(Wyear.getSelectedItem());
                            String QAdate = String.valueOf(QAday.getSelectedItem())+" "+String.valueOf(QAmonth.getSelectedItem())+" "+String.valueOf(QAyear.getSelectedItem());
                            String Sdate = String.valueOf(Sday.getSelectedItem())+" "+String.valueOf(Smonth.getSelectedItem())+" "+String.valueOf(Syear.getSelectedItem());
                            getNewEquipmentManager().saveNewEquipment("recievedEquipment","commissioned",
                                    hospital.getText().toString(),
                                    location.getText().toString(),
                                    tradeworld.getText().toString(),
                                    Rdate,
                                    Idate,
                                    Cdate,
                                    Wdate,
                                    QAdate,
                                    Sdate,
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

    private void destruct() {
        hospital.setText("");
        location.setText("");
        tradeworld.setText("");
        tenderNumber.setText("");
        orderNumber.setText("");
        cost.setText("");
        serialNumber.setText("");
        device.setText("");
        make.setText("");
        model.setText("");
        supplier.setText("");
        newSaved.dismiss();
    }

    private void setup() {
        hospital = (EditText) findViewById(R.id.edit_hospital);
        location = (EditText) findViewById(R.id.edit_location);
        tradeworld = (EditText) findViewById(R.id.edit_tradeworld_ref);
        tenderNumber = (EditText) findViewById(R.id.edit_tender_no);
        orderNumber = (EditText) findViewById(R.id.edit_order_no);
        cost = (EditText) findViewById(R.id.edit_cost);

        Rday = (Spinner) findViewById(R.id.spin_received_day);
        Rmonth = (Spinner) findViewById(R.id.spin_repair_month);
        Ryear = (Spinner) findViewById(R.id.spin_received_year);

        Iday = (Spinner) findViewById(R.id.spin_invoice_day);
        Imonth = (Spinner) findViewById(R.id.spin_invoice_month);
        Iyear = (Spinner) findViewById(R.id.spin_invoice_year);

        Cday = (Spinner) findViewById(R.id.spin_commission_day);
        Cmonth = (Spinner) findViewById(R.id.spin_commission_month);
        Cyear = (Spinner) findViewById(R.id.spin_commission_year);

        Wday = (Spinner) findViewById(R.id.spin_warranty_day);
        Wmonth = (Spinner) findViewById(R.id.spin_warranty_month);
        Wyear = (Spinner) findViewById(R.id.spin_warranty_year);

        QAday = (Spinner) findViewById(R.id.spin_qa_day);
        QAmonth = (Spinner) findViewById(R.id.spin_qa_month);
        QAyear = (Spinner) findViewById(R.id.spin_qa_year);

        Sday = (Spinner) findViewById(R.id.spin_service_day);
        Smonth = (Spinner) findViewById(R.id.spin_service_month);
        Syear = (Spinner) findViewById(R.id.spin_service_year);

        serialNumber = (EditText)findViewById(R.id.edit_serial_number);
        device = (EditText)findViewById(R.id.edit_device);
        make = (EditText)findViewById(R.id.edit_make);
        model = (EditText)findViewById(R.id.edit_model);
        supplier = (EditText)findViewById(R.id.edit_supplier);
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