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
            ((TextView)view.findViewById(R.id.txt_serial_number)).setText(getItem(i).getSerialNumber());
            ((TextView)view.findViewById(R.id.txt_device)).setText(getItem(i).getDevice());
            ((TextView)view.findViewById(R.id.txt_make)).setText(getItem(i).getMake());
            ((TextView)view.findViewById(R.id.txt_model)).setText(getItem(i).getModel());
            ((TextView)view.findViewById(R.id.txt_supplier)).setText(getItem(i).getSupplier());
            return view;
        }
    }
}