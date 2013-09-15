package com.htm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.htm.dto.Repairs;


/**
 * Created by yanga on 2013/08/15.
 */
public class ExistingEquipment extends BaseActivity {
    private Bitmap mImageBitmap;
    private ImageView imageView;
    private EditText barcodeNumber;
    private ListView listView;
    private RepairRequisitionDetailAdapter repairRequisitionDetailAdapter;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.existing_equipment);
        imageView = (ImageView) findViewById(R.id.barcode_scan);
        ((Button)findViewById(R.id.btn_scan_barcode)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ExistingEquipment.this, CaptureActivity.class),3);
            }
        });
        barcodeNumber = (EditText) findViewById(R.id.barcode_requisition_number);
        ((Button) findViewById(R.id.btn_repairs)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = Integer.valueOf(barcodeNumber.getText().toString());
                startActivity(new Intent(ExistingEquipment.this,SearchRepairsResults.class).putExtra("number",number));
            }
        });
        listView = (ListView) findViewById(R.id.requisition_list);
        repairRequisitionDetailAdapter = new RepairRequisitionDetailAdapter();
        listView.setAdapter(repairRequisitionDetailAdapter);
        View header = getLayoutInflater().inflate(R.layout.repairs_search_header,null);
        listView.addHeaderView(header);
    }

   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            Bundle extras = data.getExtras();
            if (resultCode == RESULT_FIRST_USER){
                mImageBitmap = (Bitmap) extras.get("bitmap");
                imageView.setImageBitmap(mImageBitmap);
                barcodeNumber.setText(extras.getString("content"));
            }
    }
    private class RepairRequisitionDetailAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return getRepairsRequisitionManager().getRequisitions("newRepairs").getRepairs().get("commissioned").size();
        }

        @Override
        public Repairs.Repair getItem(int i) {
            return getRepairsRequisitionManager().getRequisitions("newRepairs").getRepairs().get("commissioned").get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.repairs_search_view,null);
            }
            ((TextView)view.findViewById(R.id.txt_date_search)).setText(getItem(i).getDate());
            ((TextView)view.findViewById(R.id.txt_make_search)).setText(getItem(i).getMake());
            ((TextView)view.findViewById(R.id.txt_serial_search)).setText(getItem(i).getSerialNo());
            return view;
        }
    }
}