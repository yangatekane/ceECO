package com.htm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import com.htm.dto.Jobs;
import com.htm.dto.Parts;
import com.htm.dto.Stocks;

import java.util.List;

/**
 * Created by yanga on 2013/09/22.
 */
public class JobsActivity extends BaseActivity {
    private ListView detialList;
    private EquipmentDetailAdapter equipmentDetailAdapter;
    private ProgressDialog progressDialog;
    private double totalStocks = 0;
    private List<Jobs.Job> stockList;
    private List<Parts.Part> partList;
    private PartsDetailAdapter partsDetailAdapter = new PartsDetailAdapter();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_list_view);
        loadStocks();
        detialList = (ListView)findViewById(R.id.result_job_list);
        equipmentDetailAdapter = new EquipmentDetailAdapter();
        detialList.setAdapter(equipmentDetailAdapter);
    }
    private class EquipmentDetailAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return stockList!=null?stockList.size():0;
        }

        @Override
        public Jobs.Job getItem(int i) {
            return stockList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.jobs_detail_view,null);
            }
            int index=0;
            ((TextView)view.findViewById(R.id.edit_customer)).setText(getItem(i).getRequisitionNo());
            ((TextView)view.findViewById(R.id.edit_contact_person)).setText(getItem(i).getContactPerson());
            ((TextView)view.findViewById(R.id.edit_srequisition_no)).setText(getItem(i).getTechnician());
            ((TextView)view.findViewById(R.id.edit_make)).setText(getItem(i).getMake());
            ((TextView)view.findViewById(R.id.edit_serial_no)).setText(getItem(i).getSerialNo());
            ((TextView)view.findViewById(R.id.edit_model)).setText(getItem(i).getModel());
            ((TextView)view.findViewById(R.id.spin_date)).setText(getItem(i).getDate());
            ((TextView)view.findViewById(R.id.spin_status)).setText(getItem(i).getStatus());
            ((TextView)view.findViewById(R.id.edit_job_done_description)).setText(getItem(i).getFault_description());
            partList = getItem(i).getParts().getParts();
            for (Parts.Part part:partList){
                switch (index){
                    case 0:
                        ((TextView)view.findViewById(R.id.txt_part_number)).setText(part.getPart_number());
                        ((TextView)view.findViewById(R.id.txt_description)).setText(part.getDescription());
                        ((TextView)view.findViewById(R.id.txt_quantity)).setText(String.valueOf(part.getQuantity()));
                        ((TextView)view.findViewById(R.id.txt_amount)).setText(part.getAmount());
                        break;
                    case 1:
                        ((TableRow)view.findViewById(R.id.second_row)).setVisibility(View.VISIBLE);
                        ((TextView)view.findViewById(R.id.txt_part_number_sec)).setText(part.getPart_number());
                        ((TextView)view.findViewById(R.id.txt_description_sec)).setText(part.getDescription());
                        ((TextView)view.findViewById(R.id.txt_quantity_sec)).setText(String.valueOf(part.getQuantity()));
                        ((TextView)view.findViewById(R.id.txt_amount_sec)).setText(part.getAmount());
                        break;
                    case 2:
                        ((TableRow)view.findViewById(R.id.third_row)).setVisibility(View.VISIBLE);
                        ((TextView)view.findViewById(R.id.txt_part_number_third)).setText(part.getPart_number());
                        ((TextView)view.findViewById(R.id.txt_description_third)).setText(part.getDescription());
                        ((TextView)view.findViewById(R.id.txt_quantity_third)).setText(String.valueOf(part.getQuantity()));
                        ((TextView)view.findViewById(R.id.txt_amount_third)).setText(part.getAmount());
                        break;
                    case 3:
                        ((TableRow)view.findViewById(R.id.fourth_row)).setVisibility(View.VISIBLE);
                        ((TextView)view.findViewById(R.id.txt_part_number_fourth)).setText(part.getPart_number());
                        ((TextView)view.findViewById(R.id.txt_description_fourth)).setText(part.getDescription());
                        ((TextView)view.findViewById(R.id.txt_quantity_fourth)).setText(String.valueOf(part.getQuantity()));
                        ((TextView)view.findViewById(R.id.txt_amount_fourth)).setText(part.getAmount());
                        break;
                    case 4:
                        ((TableRow)view.findViewById(R.id.fifth_row)).setVisibility(View.VISIBLE);
                        ((TextView)view.findViewById(R.id.txt_part_number_fifth)).setText(part.getPart_number());
                        ((TextView)view.findViewById(R.id.txt_description_fifth)).setText(part.getDescription());
                        ((TextView)view.findViewById(R.id.txt_quantity_fifth)).setText(String.valueOf(part.getQuantity()));
                        ((TextView)view.findViewById(R.id.txt_amount_fifth)).setText(part.getAmount());
                        break;
                    case 5:
                        ((TableRow)view.findViewById(R.id.sixth_row)).setVisibility(View.VISIBLE);
                        ((TextView)view.findViewById(R.id.txt_part_number_sixth)).setText(part.getPart_number());
                        ((TextView)view.findViewById(R.id.txt_description_sixth)).setText(part.getDescription());
                        ((TextView)view.findViewById(R.id.txt_quantity_sixth)).setText(String.valueOf(part.getQuantity()));
                        ((TextView)view.findViewById(R.id.txt_amount_sixth)).setText(part.getAmount());
                        break;
                    case 6:
                        ((TableRow)view.findViewById(R.id.seventh_row)).setVisibility(View.VISIBLE);
                        ((TextView)view.findViewById(R.id.txt_part_number_seventh)).setText(part.getPart_number());
                        ((TextView)view.findViewById(R.id.txt_description_seventh)).setText(part.getDescription());
                        ((TextView)view.findViewById(R.id.txt_quantity_seventh)).setText(String.valueOf(part.getQuantity()));
                        ((TextView)view.findViewById(R.id.txt_amount_seventh)).setText(part.getAmount());
                        break;
                    case 7:
                        ((TableRow)view.findViewById(R.id.eighth_row)).setVisibility(View.VISIBLE);
                        ((TextView)view.findViewById(R.id.txt_part_number_eighth)).setText(part.getPart_number());
                        ((TextView)view.findViewById(R.id.txt_description_eighth)).setText(part.getDescription());
                        ((TextView)view.findViewById(R.id.txt_quantity_eighth)).setText(String.valueOf(part.getQuantity()));
                        ((TextView)view.findViewById(R.id.txt_amount_eighth)).setText(part.getAmount());
                        break;
                    case 8:
                        ((TableRow)view.findViewById(R.id.nineth_row)).setVisibility(View.VISIBLE);
                        ((TextView)view.findViewById(R.id.txt_part_number_nineth)).setText(part.getPart_number());
                        ((TextView)view.findViewById(R.id.txt_description_nineth)).setText(part.getDescription());
                        ((TextView)view.findViewById(R.id.txt_quantity_nineth)).setText(String.valueOf(part.getQuantity()));
                        ((TextView)view.findViewById(R.id.txt_amount_nineth)).setText(part.getAmount());
                        break;
                    default:
                        break;
                }
                index++;
            }
            partsDetailAdapter.notifyDataSetChanged();
            return view;
        }
    }
    private class PartsDetailAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return partList!=null?partList.size():0;
        }

        @Override
        public Parts.Part getItem(int i) {
            return partList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.detail_view,null);
            }
            ((TextView)view.findViewById(R.id.txt_part_number)).setText(getItem(i).getPart_number());
            ((TextView)view.findViewById(R.id.txt_description)).setText(getItem(i).getDescription());
            ((TextView)view.findViewById(R.id.txt_quantity)).setText(String.valueOf(getItem(i).getQuantity()));
            ((TextView)view.findViewById(R.id.txt_amount)).setText(getItem(i).getAmount());
            return view;
        }
    }
    private void loadStocks(){
        new AsyncTask<Void, Void, List<Jobs.Job>>() {
            @Override
            protected List<Jobs.Job> doInBackground(Void... voids) {
                return getStockManager().getJobs("newJobs").getJobs().get("commissioned");
            }

            @Override
            protected void onPostExecute(List<Jobs.Job> stocks) {
                stockList = stocks;
                equipmentDetailAdapter.notifyDataSetChanged();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}