package com.htm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htm.dto.Jobs;
import com.htm.dto.Repairs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yanga on 2013/09/21.
 */
public class RepairJobsActivity extends BaseActivity {
    private static final String TAG = RepairJobsActivity.class.getName();
    private GridView jobsGrid;
    private RepairRequisitionDetailAdapter repairRequisitionDetailAdapter;
    private DrawerLayout drawerLayout;
    private ExpandableListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private ProgressDialog progressDialog;
    public AlertDialog stockSaved;
    private StocksDrawerAdapter stocksDrawerAdapter;
    private HashMap<String, ArrayList<String>> drawer = new HashMap<String, ArrayList<String>>();
    ArrayList<String> auditsChildList = new ArrayList<String>();
    ArrayList<String> supplierChildList = new ArrayList<String>();
    ArrayList<String> descriptionChildList = new ArrayList<String>();
    ArrayList<String> partNumberChildList = new ArrayList<String>();
    private ArrayList<Repairs.Repair> repairsByMonth = new ArrayList<Repairs.Repair>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repair_jobs_activity);
        for (Repairs.Repair repair:getRepairsRequisitionManager().getRequisitions("newRepairs").getRepairs().get("commissioned")){
                if (!repair.getJob_status().equalsIgnoreCase("closed")){
                    repairsByMonth.add(repair);
                }
        }
        stocksDrawerAdapter = new StocksDrawerAdapter();
        setupDrawer();
        jobsGrid = ((GridView)findViewById(R.id.jobs_available));
        repairRequisitionDetailAdapter = new RepairRequisitionDetailAdapter();
        jobsGrid.setAdapter(repairRequisitionDetailAdapter);
        jobsGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final View parent = view;
                ((LinearLayout)view.findViewById(R.id.notification_archive)).setVisibility(View.VISIBLE);
                ((ImageView)view.findViewById(R.id.btn_undo)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((LinearLayout)parent.findViewById(R.id.notification_archive)).setVisibility(View.INVISIBLE);
                    }
                });
                ((TextView)view.findViewById(R.id.txt_archive)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int index=0;
                        List<Repairs.Repair> repairs = getRepairsRequisitionManager().getRequisitions("newRepairs").getRepairs().get("commissioned");
                        for (Repairs.Repair r:repairs){
                            if (r.getDescription().equalsIgnoreCase(repairsByMonth.get(i).getDescription())){
                                repairs.get(index).setJob_status("closed");
                                break;
                            }
                            index++;
                        }
                        try {
                            getRepairsRequisitionManager().updateRepair("newRepairs", "commissioned",repairs.get(index),index);
                        } catch (IOException e) {
                            Log.e(TAG,e.getMessage(),e);
                        }
                        showToast(RepairJobsActivity.this, "Repair closed");
                        startActivity(getIntent());
                        finish();
                        repairRequisitionDetailAdapter.notifyDataSetChanged();
                    }
                });
                return true;
            }
        });
        jobsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Repairs.Repair repair = repairsByMonth.get(i);
                startActivity(new Intent(RepairJobsActivity.this,JobCardsActivity.class).putExtra("myNumber",Integer.valueOf(repair.getRequisitionNumber())));
            }
        });
    }

    private void setupDrawer() {
        for(String group:getResources().getStringArray(R.array.drawer_list_jobs)){

            if (group.equalsIgnoreCase("Audit Trail")){
                auditsChildList.add("Audits");
                drawer.put(group,auditsChildList);
            }else if (group.equalsIgnoreCase("By Supplier")){
                Iterator iterator = getStockManager().getJobs("newStocks").getJobs().get("commissioned")!=null?getStockManager().getStocks("newStocks").getStocks().get("commissioned").iterator():new Iterator() {
                    @Override
                    public boolean hasNext() {
                        return false;
                    }

                    @Override
                    public Object next() {
                        return null;
                    }

                    @Override
                    public void remove() {

                    }
                };
                drawer.put(group, supplierChildList);
            }else if (group.equalsIgnoreCase("By Serial")){
                Iterator iterator = getStockManager().getJobs("newStocks").getJobs().get("commissioned")!=null?getStockManager().getStocks("newStocks").getStocks().get("commissioned").iterator():new Iterator() {
                    @Override
                    public boolean hasNext() {
                        return false;
                    }

                    @Override
                    public Object next() {
                        return null;
                    }

                    @Override
                    public void remove() {

                    }
                };
                drawer.put(group, descriptionChildList);
            }else if (group.equalsIgnoreCase("By Part Number")){
                Iterator iterator = getStockManager().getJobs("newStocks").getJobs().get("commissioned")!=null?getStockManager().getStocks("newStocks").getStocks().get("commissioned").iterator():new Iterator() {
                    @Override
                    public boolean hasNext() {
                        return false;
                    }

                    @Override
                    public Object next() {
                        return null;
                    }

                    @Override
                    public void remove() {

                    }
                };
                drawer.put(group, partNumberChildList);
            }

        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.saving_basic_info));
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ExpandableListView) findViewById(R.id.left_drawer_stocks);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerList.setAdapter(stocksDrawerAdapter);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
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
    private class RepairRequisitionDetailAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return repairsByMonth!=null?repairsByMonth.size():0;
        }

        @Override
        public Repairs.Repair getItem(int i) {
            return repairsByMonth.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.available_jobs_view,null);
            }
            jobsGrid.setTag(getItem(i));
            ((TextView)view.findViewById(R.id.repair_description)).setText(getItem(i).getDescription());
            ((TextView)view.findViewById(R.id.repair_reporter)).setText(getItem(i).getReportedBy());
            ((TextView)view.findViewById(R.id.repair_model)).setText(getItem(i).getModel());
            return view;
        }
    }
    private class StocksDrawerAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return drawer!=null?drawer.size():0;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            boolean hasAudits = (getGroup(groupPosition).equalsIgnoreCase("Audit Trail"));
            boolean hasSupplier = (getGroup(groupPosition).equalsIgnoreCase("By Supplier"));
            boolean hasDescription = (getGroup(groupPosition).equalsIgnoreCase("By Description"));
            boolean hasPartNumber = (getGroup(groupPosition).equalsIgnoreCase("By Part Number"));
            return hasPartNumber?partNumberChildList.size():hasSupplier?supplierChildList.size():hasDescription?descriptionChildList.size():hasAudits?auditsChildList.size():1;
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
                                //startActivity(new Intent(StocksActivity.this,StockActivity.class));
                                //showToast(RepairsRequisitionActivity.this,"Report found");
                                break;
                            }else{
                                //showToast(RepairsRequisitionActivity.this,"No report found");
                                continue;
                            }
                        }

                    }else if(getGroup(group).equalsIgnoreCase("Audit Trail")){
                        startActivity(new Intent(RepairJobsActivity.this,JobsActivity.class));
                    }else if (getGroup(group).equalsIgnoreCase("By Part Number")){
                        startActivity(new Intent(RepairJobsActivity.this,PartNumberActivity.class).putExtra("part",getChild(group,child)));
                    }else if (getGroup(group).equalsIgnoreCase("By Description")){
                        startActivity(new Intent(RepairJobsActivity.this,DescriptionActivity.class).putExtra("description",getChild(group,child)));
                    }else if (getGroup(group).equalsIgnoreCase("By Supplier")){
                        startActivity(new Intent(RepairJobsActivity.this,DescriptionActivity.class).putExtra("description",getChild(group,child)));
                    }else  if(getGroup(group).equalsIgnoreCase("Statistics")){
                        //showTotalsDialog(getChild(group,child));
                    }else {
                        if (getGroup(group).equalsIgnoreCase("Reports")){
                            // showToast(RepairsRequisitionActivity.this,"No report found");
                        }else if (getGroup(group).equalsIgnoreCase("Audit Trail")){
                            //(RepairsRequisitionActivity.this,"No audits found");
                        }else if (getGroup(group).equalsIgnoreCase("By Make")){
                            //showToast(RepairsRequisitionActivity.this,"By Model");
                        }else if (getGroup(group).equalsIgnoreCase("Analysis")){
                            //showToast(RepairsRequisitionActivity.this,"No analysis found");
                        }else if (getGroup(group).equalsIgnoreCase("By Serial")){
                            //showToast(RepairsRequisitionActivity.this,"No BI found");
                        }
                    }
                }
            });
            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i2) {
            return false;
        }
    }
}