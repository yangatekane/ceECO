package com.htm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


/**
 * Created by yanga on 2013/08/15.
 */
public class ExistingEquipment extends BaseActivity {
    private Bitmap mImageBitmap;
    private ImageView imageView;
    private EditText barcodeNumber;
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

}