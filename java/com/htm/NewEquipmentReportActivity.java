package com.htm;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.htm.dto.NewDtos.NewDto;

/**
 * Created by yanga on 2013/08/16.
 */
public class NewEquipmentReportActivity extends BaseActivity {
    private NewEquipmentDetailAdapter newEquipmentDetailAdapter;
    private ListView listView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_equipment_report);
        newEquipmentDetailAdapter = new NewEquipmentDetailAdapter();
        listView = ((ListView)findViewById(R.id.new_equipment_report));
        listView.setAdapter(newEquipmentDetailAdapter);
    }
    private class NewEquipmentDetailAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return getNewEquipmentManager().getNewEquipments("recievedEquipment").getNewEquiments().get("commissioned").size();
        }

        @Override
        public NewDto getItem(int i) {
            return getNewEquipmentManager().getNewEquipments("recievedEquipment").getNewEquiments().get("commissioned").get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.new_equiment_report_detail, null);
            }
            ((TextView)view.findViewById(R.id.edit_serial_number_new_report)).setText(getItem(i).getSerialNumber());
            ((TextView)view.findViewById(R.id.edit_device_new_report)).setText(getItem(i).getDevice());
            ((TextView)view.findViewById(R.id.edit_make_new_report)).setText(getItem(i).getMake());
            ((TextView)view.findViewById(R.id.edit_model_new_report)).setText(getItem(i).getModel());
            ((TextView)view.findViewById(R.id.edit_hospital_new_report)).setText(getItem(i).getHospital());
            ((TextView)view.findViewById(R.id.edit_tradeworld_ref_new_report)).setText(getItem(i).getTradeworld());
            ((TextView)view.findViewById(R.id.edit_location_new_report)).setText(getItem(i).getLocation());
            ((TextView)view.findViewById(R.id.service_plan_date_new_report)).setText(getItem(i).getSdate());
            ((TextView)view.findViewById(R.id.received_date_new_report)).setText(getItem(i).getRdate());
            ((TextView)view.findViewById(R.id.warranty_expiry_date_new_report)).setText(getItem(i).getWdate());
            ((TextView)view.findViewById(R.id.commissioned_date_new_report)).setText(getItem(i).getCdate());
            ((TextView)view.findViewById(R.id.invoice_date_new_report)).setText(getItem(i).getIdate());
            ((TextView)view.findViewById(R.id.qa_date_new_report)).setText(getItem(i).getQAdate());

            return view;
        }
    }
}