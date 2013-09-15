package com.htm;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.htm.dto.Repairs;

import java.util.ArrayList;

/**
 * Created by yanga on 2013/09/14.
 */
public class SerialActivity extends BaseActivity {
    private static final String TAG = ModelActivity.class.getName();
    private ListView listView;
    private RepairRequisitionDetailAdapter repairRequisitionDetailAdapter;
    private ArrayList<Repairs.Repair> repairsByserial = new ArrayList<Repairs.Repair>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repairs_requisition_report);
        for (Repairs.Repair repair:getRepairsRequisitionManager().getRequisitions("newRepairs").getRepairs().get("commissioned")){
            if (repair.getSerialNo().equalsIgnoreCase(getIntent().getStringExtra("serial"))){
                repairsByserial.add(repair);
            }
        }
        listView = (ListView) findViewById(R.id.repair_requisition_list);
        repairRequisitionDetailAdapter = new RepairRequisitionDetailAdapter();
        listView.setAdapter(repairRequisitionDetailAdapter);
    }
    private class RepairRequisitionDetailAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return repairsByserial.size();
        }

        @Override
        public Repairs.Repair getItem(int i) {
            return repairsByserial.get(i);
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
}