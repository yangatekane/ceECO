package com.htm;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.htm.dto.NewDtos;
import com.htm.dto.Repairs;
import com.htm.fragments.ServiceFragment;

import java.io.IOException;
import java.util.List;


/**
 * Created by yanga on 2013/08/15.
 */
public class ExistingEquipment extends BaseActivity {
    private static final String TAG = ExistingEquipment.class.getName();
    private Bitmap mImageBitmap;
    private ImageView imageView;
    private EditText barcodeNumber;
    private ListView listView;
    private DrawerLayout drawerLayout;
    private ListView searchView;
    private ActionBarDrawerToggle drawerToggle;
    private List<Repairs.Repair> existingRepairs;
    private List<NewDtos.NewDto> hospitalList;
    private RepairRequisitionDetailAdapter repairRequisitionDetailAdapter;
    private HospitalDetailAdapter hospitalDetailAdapter;
    public int index;
    public Repairs.Repair existingRepair;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.existing_equipment);
        loadRepairs();
        imageView = (ImageView) findViewById(R.id.barcode_scan);
        deActivateBarcoding();
        barcodeNumber = (EditText) findViewById(R.id.barcode_requisition_number);
        listView = (ListView) findViewById(R.id.requisition_list);
        repairRequisitionDetailAdapter = new RepairRequisitionDetailAdapter();
        hospitalDetailAdapter = new HospitalDetailAdapter();
        setupDrawer();
        searchTask();
        View header = getLayoutInflater().inflate(R.layout.repairs_search_header,null);
        ((TableLayout)header.findViewById(R.id.existing_header_state)).setBackgroundResource(R.drawable.list_gray);
        listView.addHeaderView(header);
        listView.setAdapter(repairRequisitionDetailAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int i, long l, boolean b) {
                index = i-1;
                existingRepair = getRepairsRequisitionManager().getRequisitions("newRepairs").getRepairs().get("commissioned").get(index);
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.reader_context_action_bar, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.serviceItem:
                        showServiceDialog();
                        mode.finish();
                        return true;
                    case R.id.barcodeItem:
                        activateBarcoding();
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });
        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewDtos.NewDto newDto = hospitalList.get(i);
                startActivity(new Intent(ExistingEquipment.this,NewEquipmentReportActivity.class));
            }
        });
    }

    private void activateBarcoding() {
        ((Button)findViewById(R.id.btn_scan_barcode)).setBackgroundResource(R.drawable.button_state);
        ((Button) findViewById(R.id.btn_repairs)).setBackgroundResource(R.drawable.button_state);
        ((Button)findViewById(R.id.btn_scan_barcode)).setActivated(true);
        ((Button) findViewById(R.id.btn_repairs)).setActivated(true);
        ((Button)findViewById(R.id.btn_scan_barcode)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ExistingEquipment.this, CaptureActivity.class),3);
            }
        });
        ((Button) findViewById(R.id.btn_repairs)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repairRequisitionDetailAdapter.notifyDataSetChanged();
                deActivateBarcoding();
            }
        });
    }

    private void deActivateBarcoding() {
        ((Button)findViewById(R.id.btn_scan_barcode)).setBackgroundResource(R.drawable.button_state_gray);
        ((Button) findViewById(R.id.btn_repairs)).setBackgroundResource(R.drawable.button_state_gray);
        ((Button)findViewById(R.id.btn_scan_barcode)).setActivated(false);
        ((Button) findViewById(R.id.btn_repairs)).setActivated(false);
        ((Button)findViewById(R.id.btn_scan_barcode)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ((Button)findViewById(R.id.btn_scan_barcode)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setupDrawer(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        searchView = (ListView) findViewById(R.id.left_drawer_hospitals);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        searchView.setAdapter(hospitalDetailAdapter);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ){
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadRepairs(){
        new AsyncTask<String, String, List<Repairs.Repair>>() {
            @Override
            protected List<Repairs.Repair> doInBackground(String... strings) {
                return getRepairsRequisitionManager().getRequisitions("newRepairs").getRepairs().get("commissioned");
            }

            @Override
            protected void onPostExecute(List<Repairs.Repair> repairs) {
                existingRepairs = repairs;
                repairRequisitionDetailAdapter.notifyDataSetChanged();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void searchTask(){
        new AsyncTask<Void, List<NewDtos.NewDto>, List<NewDtos.NewDto>>() {
            @Override
            protected List<NewDtos.NewDto> doInBackground(Void... voids) {
                return getNewEquipmentManager().getNewEquipments("recievedEquipment").getNewEquiments().get("commissioned");
            }

            @Override
            protected void onPostExecute(List<NewDtos.NewDto> newDtos) {
                hospitalList = newDtos;
                hospitalDetailAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onProgressUpdate(List<NewDtos.NewDto>... values) {
                hospitalList = values[0];
                hospitalDetailAdapter.notifyDataSetChanged();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            Bundle extras = data.getExtras();
            if (resultCode == RESULT_FIRST_USER){
                mImageBitmap = (Bitmap) extras.get("bitmap");
                imageView.setImageBitmap(mImageBitmap);
                barcodeNumber.setText(extras.getString("content"));
                if (existingRepair.getBarcode()!=null){
                    Intent intent = new Intent(ExistingEquipment.this,RepairsRequisitionAutoActivity.class);
                    intent.putExtra("serialNumber", existingRepair.getSerialNo());
                    intent.putExtra("make",existingRepair.getMake());
                    intent.putExtra("model", existingRepair.getModel());
                    intent.putExtra("section",existingRepair.getSection());
                    intent.putExtra("telephone", existingRepair.getTel());
                    intent.putExtra("department",existingRepair.getDepartment());
                    intent.putExtra("floor", existingRepair.getFloor());
                    intent.putExtra("barcode",existingRepair.getBarcode());
                    startActivity(intent);
                    finish();
                }else {
                    existingRepair.setBarcode(barcodeNumber.toString());
                    try {
                        getRepairsRequisitionManager().updateRepair("newRepairs", "commissioned",existingRepair,index);
                    } catch (IOException e) {
                        Log.e(TAG,e.getMessage(),e);
                    }
                }

            }
    }
    private void showServiceDialog(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        android.app.Fragment fragment = getFragmentManager().findFragmentByTag("dialog");
        if (fragment != null){
            ft.remove(fragment);
        }
        ft.addToBackStack(null);
        DialogFragment serviceFragment = new ServiceFragment();
        Bundle args = new Bundle();
        args.putString("serialNumber", existingRepair.getSerialNo());
        args.putString("make",existingRepair.getMake());
        args.putString("model", existingRepair.getModel());
        args.putString("barcode",existingRepair.getBarcode());
        serviceFragment.setArguments(args);
        serviceFragment.show(ft, "dialog");
    }
    private class RepairRequisitionDetailAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return existingRepairs!=null?existingRepairs.size():0;
        }

        @Override
        public Repairs.Repair getItem(int i) {
            return existingRepairs.get(i);
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
            listView.setTag(getItem(i));
            if (getItem(i).getBarcode()==null){
                ((TableLayout)view.findViewById(R.id.existing_state)).setBackgroundResource(R.drawable.list_gray);
                ((TextView)view.findViewById(R.id.txt_date_search)).setTextColor(Color.WHITE);
                ((TextView)view.findViewById(R.id.txt_make_search)).setTextColor(Color.WHITE);
                ((TextView)view.findViewById(R.id.txt_serial_search)).setTextColor(Color.WHITE);
            }else {
                ((TableLayout)view.findViewById(R.id.existing_state)).setBackgroundResource(R.drawable.list_blue);
                ((TextView)view.findViewById(R.id.txt_date_search)).setTextColor(Color.BLACK);
                ((TextView)view.findViewById(R.id.txt_make_search)).setTextColor(Color.BLACK);
                ((TextView)view.findViewById(R.id.txt_serial_search)).setTextColor(Color.BLACK);
            }
            ((TextView)view.findViewById(R.id.txt_date_search)).setText(getItem(i).getDate());
            ((TextView)view.findViewById(R.id.txt_make_search)).setText(getItem(i).getMake());
            ((TextView)view.findViewById(R.id.txt_serial_search)).setText(getItem(i).getSerialNo());
            return view;
        }
    }
    private class HospitalDetailAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return hospitalList!=null?hospitalList.size():0;
        }

        @Override
        public NewDtos.NewDto getItem(int i) {
            return hospitalList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.drawer_list_item_group,null);
            }
            searchView.setTag(getItem(i));
            ((TextView)view.findViewById(R.id.item_group)).setText(getItem(i).getHospital());
            return view;
        }
    }
}