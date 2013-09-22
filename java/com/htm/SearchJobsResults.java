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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.htm.dto.Parts;
import com.htm.dto.Repairs.Repair;
import com.htm.dto.Stocks.Stock;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yanga on 2013/08/18.
 */
public class SearchJobsResults extends BaseActivity{
    private static final String TAG=SearchJobsResults.class.getName();
    private ListView listView;
    private RepairRequisitionDetailAdapter repairRequisitionDetailAdapter;
    private List<Repair> resultList= new LinkedList<Repair>();
    private ViewFlipper viewFlipper;
    public AlertDialog stockSave;
    List<Stock> allstock = new LinkedList<Stock>();
    private JobCardAdapter jobCardAdapter;
    private TextView job_description;
    private TextView job_qantity;
    private TextView job_partNumber;
    private TextView job_fault;
    private TextView job_amount;
    private Spinner spin_status;

    private Spinner day;
    private Spinner month;
    private Spinner year;

    private EditText technician;
    private EditText contactPerson;
    private EditText requistionNo;
    private EditText job_make;
    private EditText job_barcode;
    private EditText job_model;
    private EditText job_serialNo;
    private Parts parts;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list_job_detail);
        List<Repair> list = getRepairsRequisitionManager().getRequisitions("newRepairs").getRepairs().get("commissioned");
        for (Repair repair:list){
            if (getIntent().getIntExtra("number",0)==repair.getRequisitionNumber()){
                resultList.add(repair);
            }
        }
        job_description = (TextView)findViewById(R.id.job_description);
        job_qantity = (TextView)findViewById(R.id.job_quantity);
        job_partNumber = (TextView)findViewById(R.id.job_partnumber);
        job_amount = (TextView)findViewById(R.id.job_price);
        job_fault = (TextView)findViewById(R.id.edit_job_done_description);
        spin_status = (Spinner)findViewById(R.id.spin_status);

        day = (Spinner)findViewById(R.id.spin_repair_day);
        month = (Spinner)findViewById(R.id.spin_repair_month);
        year = (Spinner)findViewById(R.id.spin_repair_year);

        technician = (EditText)findViewById(R.id.edit_customer);
        contactPerson = (EditText)findViewById(R.id.edit_contact_person);
        requistionNo = (EditText)findViewById(R.id.edit_srequisition_no);
        job_make = (EditText)findViewById(R.id.edit_make);
        job_barcode = (EditText)findViewById(R.id.edit_barcode_no);
        job_model = (EditText)findViewById(R.id.edit_model);
        job_serialNo = (EditText)findViewById(R.id.edit_serial_no);

        parts = new Parts();

        ((EditText) findViewById(R.id.edit_serial_no)).setText(resultList.get(0).getSerialNo());
        ((EditText)findViewById(R.id.edit_srequisition_no)).setText(String.valueOf(resultList.get(0).getRequisitionNumber()));
        ((EditText)findViewById(R.id.edit_make)).setText(resultList.get(0).getMake());
        ((EditText)findViewById(R.id.edit_model)).setText(resultList.get(0).getModel());
        ((EditText)findViewById(R.id.edit_customer)).setText(resultList.get(0).getReceivedBy());
        ((EditText)findViewById(R.id.edit_contact_person)).setText(resultList.get(0).getReportedBy());
        for (Stock s:getStockManager().getStocks("newStocks").getStocks().get("commissioned")){
            allstock.add(s);
        }
        listView = (ListView) findViewById(R.id.search_job_list);
        repairRequisitionDetailAdapter = new RepairRequisitionDetailAdapter();
        listView.setAdapter(repairRequisitionDetailAdapter);
        viewFlipper = (ViewFlipper)findViewById(R.id.job_pager);
        final Spinner stocks = ((Spinner)findViewById(R.id.spin_stocks));
        stocks.setAdapter(new EquipmentDetailAdapter());
        stockSave = new AlertDialog.Builder(this)
                .setMessage("Stock information saved....")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        stockSave.dismiss();
                    }
                }).create();
        ((Button)findViewById(R.id.btn_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Parts.Part part = new Parts.Part(job_partNumber.getText().toString(),job_description.getText().toString(),Integer.valueOf(job_qantity.getText().toString()),job_amount.getText().toString());
                parts.getParts().add(part);
                showToast(SearchJobsResults.this, "Part Added");
                job_amount.setText("");
                job_partNumber.setText("");
                job_qantity.setText("");
                job_description.setText("");
            }
        });
        ((Button)findViewById(R.id.btn_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String date = String.valueOf(day.getSelectedItem()) + " " + String.valueOf(month.getSelectedItem()) + " " + String.valueOf(year.getSelectedItem());
                    String status = String.valueOf(spin_status.getSelectedItem());
                    getStockManager().saveJobs("newJobs", "commissioned", parts,
                            date,
                            status,
                            job_fault.getText().toString(),
                            job_serialNo.getText().toString(),
                            job_make.getText().toString(),
                            job_model.getText().toString(),
                            contactPerson.getText().toString(),
                            requistionNo.getText().toString());
                    stockSave.setMessage("Stock information saved....");
                    stockSave.show();
                } catch (IOException e) {
                    stockSave.setMessage("failed to save stock information!");
                    stockSave.show();
                    Log.e(TAG, e.getMessage(), e);
                } catch (InstantiationException e) {
                    Log.e(TAG, e.getMessage(), e);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                //progressDialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            ((EditText)view.findViewById(R.id.edit_model_report)).setText(getItem(i).getModel());
            ((EditText)view.findViewById(R.id.edit_make_report)).setText(getItem(i).getMake());
            ((EditText)view.findViewById(R.id.edit_serial_number_report)).setText(getItem(i).getSerialNo());

            return view;
        }
    }

    private class EquipmentDetailAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return getStockManager().getStocks("newStocks").getStocks().get("commissioned").size();
        }

        @Override
        public Stock getItem(int i) {
            return getStockManager().getStocks("newStocks").getStocks().get("commissioned").get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.detail_view, null);
            }
            ((TextView)view.findViewById(R.id.txt_part_number)).setText(getItem(i).getPartNumber());
            ((TextView)view.findViewById(R.id.txt_description)).setText(getItem(i).getDescription());
            ((TextView)view.findViewById(R.id.txt_quantity)).setText(String.valueOf(getItem(i).getQuantity()));
            ((TextView)view.findViewById(R.id.txt_amount)).setText(getItem(i).getAmount());
            return view;
        }
    }
    private class JobCardAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return getStockManager().getStocks("newJobs").getStocks()!=null&&getStockManager().getStocks("newJobs").getStocks().get("commissioned")!=null?getStockManager().getStocks("newJobs").getStocks().get("commissioned").size():0;
        }

        @Override
        public Stock getItem(int i) {
            return getStockManager().getStocks("newJobs").getStocks().get("commissioned").get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.detail_view, null);
            }
            ((TextView)view.findViewById(R.id.txt_part_number)).setText(getItem(i).getPartNumber());
            ((TextView)view.findViewById(R.id.txt_description)).setText(getItem(i).getDescription());
            ((TextView)view.findViewById(R.id.txt_quantity)).setText(String.valueOf(getItem(i).getQuantity()));
            ((TextView)view.findViewById(R.id.txt_amount)).setText(getItem(i).getAmount());
            return view;
        }
    }
}