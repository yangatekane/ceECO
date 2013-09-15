package com.htm;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.htm.dto.Repairs;
import com.htm.fragments.TotalsFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by yanga on 2013/08/15.
 */
public class RepairsRequisitionActivity extends BaseActivity {
    private static final String TAG = RepairsRequisitionActivity.class.getName();
    public AlertDialog repairSaved;
    private DrawerLayout drawerLayout;
    private ExpandableListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private HashMap<String, ArrayList<String>> drawer = new HashMap<String, ArrayList<String>>();
    private DrawerAdapter drawerExpandableListAdapter = new DrawerAdapter();

    private EditText serialNum;
    private EditText make;
    private EditText model;
    private EditText requisitionNumber;
    private EditText section;
    private EditText tel;
    private EditText department;
    private EditText floor;
    private Spinner day;
    private Spinner month;
    private Spinner year;
    private EditText description;
    private EditText reportedBy;
    private EditText receivedBy;

    ArrayList<String> auditsChildList = new ArrayList<String>();
    ArrayList<String> reportsChildList = new ArrayList<String>();
    ArrayList<String> makeChildList = new ArrayList<String>();
    ArrayList<String> modelChildList = new ArrayList<String>();
    ArrayList<String> serialChildList = new ArrayList<String>();
    ArrayList<String> statsChildList = new ArrayList<String>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repairs_requisition);

        setupDrawer();
        setup();
        repairSaved = new AlertDialog.Builder(this)
                .setMessage("Repair requisition information saved....")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        destruct();
                        repairSaved.dismiss();
                    }
                }).create();
        ((Button)findViewById(R.id.btn_save_repairs)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = String.valueOf(day.getSelectedItem()) + " " + String.valueOf(month.getSelectedItem()) + " " + String.valueOf(year.getSelectedItem());
                try {
                    getRepairsRequisitionManager().saveRequisitions("newRepairs", "commissioned",
                            serialNum.getText().toString(), make.getText().toString(),
                            model.getText().toString(),
                            Integer.valueOf(requisitionNumber.getText().toString()),
                            tel.getText().toString(), date, section.getText().toString(), department.getText().toString(), floor.getText().toString(),
                            description.getText().toString(), reportedBy.getText().toString(), receivedBy.getText().toString());
                    repairSaved.show();
                    drawerExpandableListAdapter.notifyDataSetChanged();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage(), e);
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

    private void setupDrawer() {
        for(String group:getResources().getStringArray(R.array.drawer_list_requisitions)){

            if (group.equalsIgnoreCase("Audit Trail")){
                auditsChildList.add("Audits");
                drawer.put(group,auditsChildList);
            }else if (group.equalsIgnoreCase("Reports")){
                for (String child:getResources().getStringArray(R.array.drawer_months)){
                    reportsChildList.add(child);
                }
                drawer.put(group,reportsChildList);
            }else if (group.equalsIgnoreCase("By Make")){
                Iterator iterator = getRepairsRequisitionManager().getRequisitions("newRepairs").getRepairs().get("commissioned").iterator();
                while (iterator.hasNext()){
                    Repairs.Repair repair = (Repairs.Repair)iterator.next();
                    if (!makeChildList.contains(repair.getMake()))
                        makeChildList.add(repair.getMake());
                }
                drawer.put(group,makeChildList);
            }else if (group.equalsIgnoreCase("By Model")){
                Iterator iterator = getRepairsRequisitionManager().getRequisitions("newRepairs").getRepairs().get("commissioned").iterator();
                while (iterator.hasNext()){
                    Repairs.Repair repair = (Repairs.Repair)iterator.next();
                    if (!modelChildList.contains(repair.getModel()))
                        modelChildList.add(repair.getModel());
                }
                drawer.put(group,modelChildList);
            }else if (group.equalsIgnoreCase("By Serial")){
                Iterator iterator = getRepairsRequisitionManager().getRequisitions("newRepairs").getRepairs().get("commissioned").iterator();
                while (iterator.hasNext()){
                    Repairs.Repair repair = (Repairs.Repair)iterator.next();
                    if (!serialChildList.contains(repair.getSerialNo()))
                        serialChildList.add(repair.getSerialNo());
                }
                drawer.put(group,serialChildList);
            }else {
                for (String child:getResources().getStringArray(R.array.drawer_months)){
                    statsChildList.add(child);
                }
                drawer.put(group,statsChildList);
            }


        }
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ExpandableListView) findViewById(R.id.left_drawer_requisitions);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerList.setAdapter(drawerExpandableListAdapter);
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
    }

    private void destruct() {
        serialNum.setText("");
        make.setText("");
        model.setText("");
        requisitionNumber.setText("");
        section.setText("");
        tel.setText("");
        department.setText("");
        floor.setText("");
        reportedBy.setText("");
        receivedBy.setText("");
        description.setText("");
    }

    private void setup() {
        serialNum = (EditText) findViewById(R.id.edit_serial_number);
        make = (EditText) findViewById(R.id.edit_make);
        model = (EditText) findViewById(R.id.edit_model);
        requisitionNumber = (EditText) findViewById(R.id.edit_requisition_number);
        section = (EditText) findViewById(R.id.edit_section);
        tel = (EditText) findViewById(R.id.edit_telephone_number);
        department = (EditText) findViewById(R.id.edit_department);
        floor = (EditText) findViewById(R.id.edit_floor);
        day = (Spinner) findViewById(R.id.spin_repair_day);
        month = (Spinner) findViewById(R.id.spin_repair_month);
        year = (Spinner) findViewById(R.id.spin_repair_year);
        description = (EditText) findViewById(R.id.edit_description);
        reportedBy = (EditText)findViewById(R.id.edit_reportedBy);
        receivedBy = (EditText)findViewById(R.id.edit_receivedby);
    }
    private void showTotalsDialog(String month){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        android.app.Fragment fragment = getFragmentManager().findFragmentByTag("dialog");
        if (fragment != null){
            ft.remove(fragment);
        }
        ft.addToBackStack(null);
        DialogFragment contentFragment = new TotalsFragment(RepairsRequisitionActivity.this);
        Bundle bundle = new Bundle();
        bundle.putString("month",month);
        contentFragment.setArguments(bundle);
        contentFragment.show(ft,"dialog");
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

    private class DrawerAdapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return drawer.size();
        }

        @Override
        public int getChildrenCount(int childPosition) {
            int monthsSize = getResources().getStringArray(R.array.drawer_months).length;
            boolean hasAudits = (getGroup(childPosition).equalsIgnoreCase("Audit Trail"));
            boolean hasMakes = (getGroup(childPosition).equalsIgnoreCase("By Make"));
            boolean hasModels = (getGroup(childPosition).equalsIgnoreCase("By Model"));
            boolean hasSerials = (getGroup(childPosition).equalsIgnoreCase("By Serial"));
            boolean hasReports = (getGroup(childPosition).equalsIgnoreCase("Reports"));
            return hasAudits?auditsChildList.size():hasMakes?makeChildList.size():hasModels?modelChildList.size():hasSerials?serialChildList.size():hasReports?monthsSize:statsChildList.size();
        }

        @Override
        public String getGroup(int groupPosition) {
            return drawer.keySet().toArray(new String[drawer.size()])[groupPosition];
        }

        @Override
        public String getChild(int groupPosition, int childPosition) {
            return drawer.get(getGroup(groupPosition)).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
            final int group = groupPosition;
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.drawer_list_item_group,null);
            }
            TextView groupLabel = ((TextView)view.findViewById(R.id.item_group));
            groupLabel.setText(getGroup(groupPosition));
            return view;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
            final int group = groupPosition;
            final int child = childPosition;
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.drawer_list_item_child,null);
            }
            TextView childLabel = ((TextView)view.findViewById(R.id.item_child));
            childLabel.setText(getChild(groupPosition,childPosition));
            childLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getGroup(group).equalsIgnoreCase("Reports")){
                        for (Repairs.Repair repair:getRepairsRequisitionManager().getRequisitions("newRepairs").getRepairs().get("commissioned")){
                            if(repair.getDate().split(" ")[1].equalsIgnoreCase(getChild(group,child))){
                                startActivity(new Intent(RepairsRequisitionActivity.this,RepairsRequisitionReportActivity.class).putExtra("month",getChild(group,child)));
                                showToast(RepairsRequisitionActivity.this,"Report found");
                                break;
                            }else{
                                showToast(RepairsRequisitionActivity.this,"No report found");
                                continue;
                            }
                        }

                    }else if(getGroup(group).equalsIgnoreCase("Audit Trail")){
                        startActivity(new Intent(RepairsRequisitionActivity.this,AuditTrailsActivity.class));
                    }else if (getGroup(group).equalsIgnoreCase("By Make")){
                        startActivity(new Intent(RepairsRequisitionActivity.this,MakeActivity.class).putExtra("make",getChild(group,child)));
                    }else if (getGroup(group).equalsIgnoreCase("By Model")){
                        startActivity(new Intent(RepairsRequisitionActivity.this,ModelActivity.class).putExtra("model",getChild(group,child)));
                    }else if (getGroup(group).equalsIgnoreCase("By Serial")){
                        startActivity(new Intent(RepairsRequisitionActivity.this,SerialActivity.class).putExtra("serial",getChild(group,child)));
                    }else  if(getGroup(group).equalsIgnoreCase("Statistics")){
                        showTotalsDialog(getChild(group,child));
                    }else {
                        if (getGroup(group).equalsIgnoreCase("Reports")){
                            showToast(RepairsRequisitionActivity.this,"No report found");
                        }else if (getGroup(group).equalsIgnoreCase("Audit Trail")){
                            showToast(RepairsRequisitionActivity.this,"No audits found");
                        }else if (getGroup(group).equalsIgnoreCase("By Make")){
                            showToast(RepairsRequisitionActivity.this,"By Model");
                        }else if (getGroup(group).equalsIgnoreCase("Analysis")){
                            showToast(RepairsRequisitionActivity.this,"No analysis found");
                        }else if (getGroup(group).equalsIgnoreCase("By Serial")){
                            showToast(RepairsRequisitionActivity.this,"No BI found");
                        }
                    }
                }
            });
            return view;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

}