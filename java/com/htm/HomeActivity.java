package com.htm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class HomeActivity extends BaseActivity {
    private static final String TAG = HomeActivity.class.getName();
    private EditText repair;
    public AlertDialog newSave;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        repair =(EditText)findViewById(R.id.jobcard_requisition);
        newSave = new AlertDialog.Builder(this)
                .setMessage("Please enter the requisition number....")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
        ((ImageView)findViewById(R.id.new_quipments)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,NewEquipmentActivity.class));
            }
        });
        ((ImageView)findViewById(R.id.existing_quipments)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,ExistingEquipment.class));
            }
        });
        ((ImageView)findViewById(R.id.repairs_requisition)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,RepairsRequisitionActivity.class));
            }
        });
        ((ImageView)findViewById(R.id.job_cards)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (repair.getText().toString().equalsIgnoreCase("")){
                    newSave.show();
                }else {
                    newSave.dismiss();
                    startActivity(new Intent(HomeActivity.this,JobCardsActivity.class).putExtra("myNumber",Integer.valueOf(repair.getText().toString())));
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                break;
            case R.id.home_action_login_logout:
                startActivity(new Intent(HomeActivity.this,StocksActivity.class));
                break;
            case R.id.home_action_graphs:
                startActivity(new Intent(HomeActivity.this,GraphsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
