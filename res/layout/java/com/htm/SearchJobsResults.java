package com.htm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.htm.dto.Repair;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yanga on 2013/08/18.
 */
public class SearchJobsResults extends BaseActivity {
    private static final String TAG=SearchJobsResults.class.getName();
    private ListView listView;
    private RepairRequisitionDetailAdapter repairRequisitionDetailAdapter;
    private List<Repair> resultList= new LinkedList<Repair>();
    private ViewFlipper viewFlipper;
    public AlertDialog stockSave;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list_job_detail);
        List<Repair> list = getRepairsRequisitionManager().getRequisitions("newRepairs").getRepairs().get("commissioned");
        for (Repair repair:list){
            if (getIntent().getIntExtra("number",0)==repair.getRequisitionNumber()){
                resultList.add(repair);
            }
        }
        listView = (ListView) findViewById(R.id.search_job_list);
        repairRequisitionDetailAdapter = new RepairRequisitionDetailAdapter();
        listView.setAdapter(repairRequisitionDetailAdapter);
        viewFlipper = (ViewFlipper)findViewById(R.id.job_pager);
        final EditText partNumber = (EditText) findViewById(R.id.edit_part_number);
        final EditText description = (EditText) findViewById(R.id.edit_description);
        final EditText quantity = (EditText) findViewById(R.id.edit_quantity);
        final EditText amount = (EditText) findViewById(R.id.edit_amount);
        stockSave = new AlertDialog.Builder(this)
                .setMessage("Stock information saved....")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        partNumber.setText("");
                        description.setText("");
                        quantity.setText("");
                        amount.setText("");
                        stockSave.dismiss();
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
                        stockSave.setMessage("Please fill in all the fields");
                        stockSave.show();
                    }else {
                        double total = Double.valueOf(amount.getText().toString())*Double.valueOf(quantity.getText().toString());
                        getStockManager().saveStock("newStocks","commissioned",partNumber.getText().toString(),
                                description.getText().toString(),
                                -Integer.valueOf(quantity.getText().toString()),"R "+String.valueOf(total));
                        stockSave.setMessage("Stock information saved....");
                        stockSave.show();
                    }
                } catch (IOException e) {
                    stockSave.setMessage("failed to save stock information!");
                    stockSave.show();
                    Log.e(TAG, e.getMessage(), e);
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
        getMenuInflater().inflate(R.menu.job_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                break;
            case R.id.home_action_new_job:
                viewFlipper.showNext();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private class RepairRequisitionDetailAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public Repair getItem(int i) {

            return resultList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.repairs_list_report_detail,null);
            }

            ((EditText)view.findViewById(R.id.edit_requisition_number_report)).setText(String.valueOf(getItem(i).getRequisitionNumber()));
            ((EditText)view.findViewById(R.id.edit_section_report)).setText(getItem(i).getSection());
            ((EditText)view.findViewById(R.id.edit_telephone_number_report)).setText(getItem(i).getTel());
            ((EditText)view.findViewById(R.id.edit_department_report)).setText(getItem(i).getDepartment());
            ((EditText)view.findViewById(R.id.edit_floor_report)).setText(getItem(i).getFloor());
            ((EditText)view.findViewById(R.id.edit_date_report)).setText(getItem(i).getDate());
            ((EditText)view.findViewById(R.id.edit_description_report)).setText(getItem(i).getDescription());
            ((EditText)view.findViewById(R.id.edit_reportedBy_report)).setText(getItem(i).getReportedBy());
            ((EditText)view.findViewById(R.id.edit_receivedby__report)).setText(getItem(i).getReceivedBy());

            return view;
        }
    }
}