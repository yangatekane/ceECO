package com.htm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.IOException;

/**
 * Created by yanga on 2013/08/15.
 */
public class RepairsRequisitionActivity extends BaseActivity {
    private static final String TAG = RepairsRequisitionActivity.class.getName();
    public AlertDialog repairSaved;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repairs_requisition);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item, getResources().getStringArray(R.array.drawer_list)));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ){
            public void onDrawerClosed(View view) {
                //getActionBar().setTitle(title);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        final EditText requisitionNumber = (EditText) findViewById(R.id.edit_requisition_number);
        final EditText section = (EditText) findViewById(R.id.edit_section);
        final EditText tel = (EditText) findViewById(R.id.edit_telephone_number);
        final EditText department = (EditText) findViewById(R.id.edit_department);
        final EditText floor = (EditText) findViewById(R.id.edit_floor);
        final Spinner day = (Spinner) findViewById(R.id.spin_repair_day);
        final Spinner month = (Spinner) findViewById(R.id.spin_repair_month);
        final Spinner year = (Spinner) findViewById(R.id.spin_repair_year);
        final EditText description = (EditText) findViewById(R.id.edit_description);
        final EditText reportedBy = (EditText)findViewById(R.id.edit_reportedBy);
        final EditText receivedBy = (EditText)findViewById(R.id.edit_receivedby);
        repairSaved = new AlertDialog.Builder(this)
                .setMessage("Repair requisition information saved....")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requisitionNumber.setText("");
                        section.setText("");
                        tel.setText("");
                        department.setText("");
                        floor.setText("");
                        reportedBy.setText("");
                        receivedBy.setText("");
                        description.setText("");
                        repairSaved.dismiss();
                    }
                }).create();
        ((Button)findViewById(R.id.btn_save_repairs)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = String.valueOf(day.getSelectedItem())+" "+String.valueOf(month.getSelectedItem())+" "+String.valueOf(year.getSelectedItem());
                try {
                    getRepairsRequisitionManager().saveRequisitions("newRepairs","commissioned",Integer.valueOf(requisitionNumber.getText().toString()),
                    tel.getText().toString(),date,section.getText().toString(),department.getText().toString(),floor.getText().toString(),
                    description.getText().toString(),reportedBy.getText().toString(),receivedBy.getText().toString());
                    repairSaved.show();
                } catch (IOException e) {
                    Log.e(TAG,e.getMessage(),e);
                    repairSaved.dismiss();
                } catch (InstantiationException e) {
                    Log.e(TAG, e.getMessage(), e);
                    repairSaved.dismiss();
                } catch (IllegalAccessException e) {
                    Log.e(TAG, e.getMessage(), e);
                    repairSaved.dismiss();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            startActivity(new Intent(RepairsRequisitionActivity.this,RepairsRequisitionReportActivity.class));
        }
    }


}