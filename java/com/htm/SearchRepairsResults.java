package com.htm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.htm.dto.Repairs.Repair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yanga on 2013/08/18.
 */
public class SearchRepairsResults extends BaseActivity {
    private ListView listView;
    private RepairRequisitionDetailAdapter repairRequisitionDetailAdapter;
    private List<Repair> resultList= new LinkedList<Repair>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list_repairs_detail);
        List<Repair> list = getRepairsRequisitionManager().getRequisitions("newRepairs").getRepairs().get("commissioned");
        for (Repair repair:list){
            if (getIntent().getIntExtra("number",0)==repair.getRequisitionNumber()){
                resultList.add(repair);
            }
        }
        listView = (ListView) findViewById(R.id.search_repairs_list);
        repairRequisitionDetailAdapter = new RepairRequisitionDetailAdapter();
        listView.setAdapter(repairRequisitionDetailAdapter);
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