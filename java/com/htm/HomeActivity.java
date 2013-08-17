package com.htm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class HomeActivity extends BaseActivity {
    private static final String TAG = HomeActivity.class.getName();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
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
                startActivity(new Intent(HomeActivity.this,JobCardsActivity.class));
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
            case R.id.home_action_bar_cache:
                try {
                    getStockManager().clearCache("newStocks");
                    getNewEquipmentManager().clearCache("recievedEquipment");
                } catch (IOException e) {
                    Log.e(TAG,e.getMessage(),e);
                } catch (ClassNotFoundException e) {
                    Log.e(TAG,e.getMessage(),e);
                }
                break;
            case R.id.home_action_login_logout:
                startActivity(new Intent(HomeActivity.this,RegistrationActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
