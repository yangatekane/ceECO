package com.htm;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.ViewFlipper;

import com.htm.fragments.ExportFragment;

import java.io.IOException;

/**
 * Created by yanga on 2013/08/11.
 */
public class RegistrationActivity extends BaseActivity {
    private static final String TAG = RegistrationActivity.class.getName();
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private ProgressDialog progressDialog;
    public AlertDialog stockSaved;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.saving_basic_info));
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item_child, getResources().getStringArray(R.array.drawer_list)));
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



        final ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.registration_viewFlipper);
        final EditText partNumber = (EditText) findViewById(R.id.edit_part_number);
        final EditText description = (EditText) findViewById(R.id.edit_description);
        final EditText quantity = (EditText) findViewById(R.id.edit_quantity);
        final EditText amount = (EditText) findViewById(R.id.edit_amount);
        stockSaved = new AlertDialog.Builder(this)
                .setMessage("Stock information saved....")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        partNumber.setText("");
                        description.setText("");
                        quantity.setText("");
                        amount.setText("");
                        stockSaved.dismiss();
                    }
                }).create();
            ((Button)findViewById(R.id.btn_next)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (partNumber.getText().toString().equalsIgnoreCase("")||
                                description.getText().toString().equalsIgnoreCase("")||
                                quantity.getText().toString().equalsIgnoreCase("")||
                                amount.getText().toString().equalsIgnoreCase("")){
                            stockSaved.setMessage("Please fill in all the fields");
                            stockSaved.show();
                        }else {
                            double total = Double.valueOf(amount.getText().toString())*Double.valueOf(quantity.getText().toString());
                            getStockManager().saveStock("newStocks","commissioned",partNumber.getText().toString(),
                                    description.getText().toString(),
                                    Integer.valueOf(quantity.getText().toString()),"R "+String.valueOf(total));
                            stockSaved.setMessage("Stock information saved....");
                            stockSaved.show();
                        }
                    } catch (IOException e) {
                        stockSaved.setMessage("failed to save stock information!");
                        stockSaved.show();
                        Log.e(TAG,e.getMessage(),e);
                    } catch (InstantiationException e) {
                        Log.e(TAG,e.getMessage(),e);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    //progressDialog.show();
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
            progressDialog.show();
            showExportDialog(progressDialog);
        }
    }
    private void showExportDialog(ProgressDialog dialog){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        android.app.Fragment fragment = getFragmentManager().findFragmentByTag("dialog");
        if (fragment != null){
            ft.remove(fragment);
        }
        ft.addToBackStack(null);
        DialogFragment contentFragment = new ExportFragment(RegistrationActivity.this, dialog);
        contentFragment.show(ft,"dialog");
    }
}