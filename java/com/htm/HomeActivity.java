package com.htm;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.htm.dto.Service;
import com.htm.fragments.ReportsFragment;

public class HomeActivity extends BaseActivity {
    private static final String TAG = HomeActivity.class.getName();
    public AlertDialog newSave;
    private DrawerLayout drawerLayout;
    private ListView serviceView;
    private ActionBarDrawerToggle drawerToggle;
    private ServiceDetailAdapter serviceDetailAdapter;
    private ListView reportsView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        serviceDetailAdapter = new ServiceDetailAdapter();
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
                startActivity(new Intent(HomeActivity.this,RepairJobsActivity.class));
            }
        });
        setupDrawer();
        updateNotification();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNotification();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNotification();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        updateNotification();
    }

    private void setupDrawer(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        serviceView = (ListView) findViewById(R.id.left_drawer_services);
        reportsView = (ListView) findViewById(R.id.right_drawer);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        serviceView.setAdapter(serviceDetailAdapter);
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ){
            @Override
            public void onDrawerStateChanged(int newState) {

            }
        };
        reportsView.setAdapter(new ArrayAdapter<String>(HomeActivity.this,R.layout.drawer_list_item_group,getResources().getStringArray(R.array.months_first_letter)));
        reportsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showReportsDialog();
            }
        });
        drawerLayout.setDrawerListener(drawerToggle);
    }
    private void drawerToggle(int gravity) {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawerLayout.isDrawerOpen(gravity)) {
            drawerLayout.closeDrawer(gravity);
        } else {
            drawerLayout.openDrawer(gravity);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        drawerToggle.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_settings:
                break;
            case R.id.home_action_login_logout:
                startActivity(new Intent(HomeActivity.this,StocksActivity.class));
                break;
            case R.id.home_action_graphs:
                startActivity(new Intent(HomeActivity.this,GraphsActivity.class));
                break;
            case R.id.notification:
                drawerToggle(Gravity.RIGHT);
                break;
            default:
                drawerToggle(Gravity.LEFT);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateNotification(){
        invalidateOptionsMenu();
    }

    private void showReportsDialog(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        android.app.Fragment fragment = getFragmentManager().findFragmentByTag("dialog");
        if (fragment != null){
            ft.remove(fragment);
        }
        ft.addToBackStack(null);
        DialogFragment serviceFragment = new ReportsFragment();
        serviceFragment.show(ft, "dialog");
    }
    private class ServiceDetailAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return getServiceManager().getServices()!=null?getServiceManager().getServices().getServices().size():0;
        }

        @Override
        public Service getItem(int i) {
            return getServiceManager().getServices().getServices().get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.drawer_list_item_group,null);
            }
            ((TextView)view.findViewById(R.id.item_group)).setText(getItem(i).getSerialNo()+" "+getItem(i).getServiceDate());
            return view;
        }
    }
}
