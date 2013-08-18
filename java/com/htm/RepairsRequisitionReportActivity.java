package com.htm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.htm.dto.Repair;

/**
 * Created by yanga on 2013/08/18.
 */
public class RepairsRequisitionReportActivity extends BaseActivity {
    private static final String TAG = RepairsRequisitionReportActivity.class.getName();
    private ListView listView;
    private RepairRequisitionDetailAdapter repairRequisitionDetailAdapter;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repairs_requisition_report);
        listView = (ListView) findViewById(R.id.repair_requisition_list);
        repairRequisitionDetailAdapter = new RepairRequisitionDetailAdapter();
        listView.setAdapter(repairRequisitionDetailAdapter);
    }
    private class RepairRequisitionDetailAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return getRepairsRequisitionManager().getRequisitions("newRepairs").getRepairs().get("commissioned").size();
        }

        @Override
        public Repair getItem(int i) {
            return getRepairsRequisitionManager().getRequisitions("newRepairs").getRepairs().get("commissioned").get(i);
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
            TextView rnumber = ((TextView)view.findViewById(R.id.edit_requisition_number_report));
            rnumber.setText(getItem(i).getRequisitionNumber());
            ((TextView)view.findViewById(R.id.edit_section_report)).setText(getItem(i).getSection());
            ((TextView)view.findViewById(R.id.edit_telephone_number_report)).setText(getItem(i).getTel());
            ((TextView)view.findViewById(R.id.edit_department_report)).setText(getItem(i).getDepartment());
            ((TextView)view.findViewById(R.id.edit_floor_report)).setText(getItem(i).getFloor());
            ((TextView)view.findViewById(R.id.edit_date_report)).setText(getItem(i).getDate());
            ((TextView)view.findViewById(R.id.edit_description_report)).setText(getItem(i).getDescription());
            ((TextView)view.findViewById(R.id.edit_reportedBy_report)).setText(getItem(i).getReportedBy());
            ((TextView)view.findViewById(R.id.edit_receivedby__report)).setText(getItem(i).getReceivedBy());
            return view;
        }
    }
}