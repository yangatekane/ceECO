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
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.htm.dto.Repairs;
import com.htm.dto.Stocks;
import com.htm.fragments.ExportFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by yanga on 2013/08/11.
 */
public class StocksActivity extends BaseActivity {
    private static final String TAG = StocksActivity.class.getName();
    private DrawerLayout drawerLayout;
    private ExpandableListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private ProgressDialog progressDialog;
    public AlertDialog stockSaved;
    private HashMap<String, ArrayList<String>> drawer = new HashMap<String, ArrayList<String>>();
    ArrayList<String> auditsChildList = new ArrayList<String>();
    ArrayList<String> supplierChildList = new ArrayList<String>();
    ArrayList<String> descriptionChildList = new ArrayList<String>();
    ArrayList<String> partNumberChildList = new ArrayList<String>();
    private StocksDrawerAdapter stocksDrawerAdapter;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        stocksDrawerAdapter = new StocksDrawerAdapter();
        setupDrawer();


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
                                    Integer.valueOf(quantity.getText().toString()),"R "+String.valueOf(total), null, null);
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

    private void setupDrawer() {
        for(String group:getResources().getStringArray(R.array.drawer_list_stocks)){

            if (group.equalsIgnoreCase("Audit Trail")){
                auditsChildList.add("Audits");
                drawer.put(group,auditsChildList);
            }else if (group.equalsIgnoreCase("By Supplier")){
                Iterator iterator = getStockManager().getStocks("newStocks").getStocks().get("commissioned")!=null?getStockManager().getStocks("newStocks").getStocks().get("commissioned").iterator():new Iterator() {
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
                while (iterator.hasNext()){
                    Stocks.Stock stock = (Stocks.Stock)iterator.next();
                    if (!supplierChildList.contains(stock.getDescription()))
                        supplierChildList.add(stock.getDescription());
                }
                drawer.put(group, supplierChildList);
            }else if (group.equalsIgnoreCase("By Description")){
                Iterator iterator = getStockManager().getStocks("newStocks").getStocks().get("commissioned")!=null?getStockManager().getStocks("newStocks").getStocks().get("commissioned").iterator():new Iterator() {
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
                while (iterator.hasNext()){
                    Stocks.Stock stock = (Stocks.Stock)iterator.next();
                    if (!descriptionChildList.contains(stock.getDescription()))
                        descriptionChildList.add(stock.getDescription());
                }
                drawer.put(group, descriptionChildList);
            }else if (group.equalsIgnoreCase("By Part Number")){
                Iterator iterator = getStockManager().getStocks("newStocks").getStocks().get("commissioned")!=null?getStockManager().getStocks("newStocks").getStocks().get("commissioned").iterator():new Iterator() {
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
                while (iterator.hasNext()){
                    Stocks.Stock stock = (Stocks.Stock)iterator.next();
                    if (!partNumberChildList.contains(stock.getPart_number()))
                        partNumberChildList.add(stock.getPart_number());
                }
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
            startActivity(new Intent(StocksActivity.this,StockActivity.class));
        }
    }
    private class StocksDrawerAdapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return drawer.size();
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
                        startActivity(new Intent(StocksActivity.this,StockActivity.class));
                    }else if (getGroup(group).equalsIgnoreCase("By Part Number")){
                        startActivity(new Intent(StocksActivity.this,PartNumberActivity.class).putExtra("part",getChild(group,child)));
                    }else if (getGroup(group).equalsIgnoreCase("By Description")){
                        startActivity(new Intent(StocksActivity.this,DescriptionActivity.class).putExtra("description",getChild(group,child)));
                    }else if (getGroup(group).equalsIgnoreCase("By Supplier")){
                        startActivity(new Intent(StocksActivity.this,DescriptionActivity.class).putExtra("description",getChild(group,child)));
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
    private void showExportDialog(ProgressDialog dialog){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        android.app.Fragment fragment = getFragmentManager().findFragmentByTag("dialog");
        if (fragment != null){
            ft.remove(fragment);
        }
        ft.addToBackStack(null);
        DialogFragment contentFragment = new ExportFragment(StocksActivity.this, dialog);
        contentFragment.show(ft,"dialog");
    }
}