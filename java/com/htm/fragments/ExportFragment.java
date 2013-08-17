package com.htm.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.htm.BaseActivity;
import com.htm.R;
import com.htm.dto.Stock;


/**
 * Created by yanga on 2013/08/07.
 */
public class ExportFragment extends DialogFragment {
    final static String TAG = ExportFragment.class.getName();
    private View view;
    private ListView listView;
    private ListView detialList;
    private BaseActivity baseActivity;
    private EquipmentAdapter equipmentAdapter;
    private EquipmentDetailAdapter equipmentDetailAdapter;
    public ExportFragment(Activity activity, ProgressDialog dialog){
        baseActivity = (BaseActivity) activity;
        dialog.dismiss();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog);
        equipmentAdapter = new EquipmentAdapter();
        equipmentDetailAdapter = new EquipmentDetailAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        equipmentAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.file_view, container,false);
        detialList = (ListView) view.findViewById(R.id.detail_list);
        detialList.setAdapter(equipmentDetailAdapter);
        getDialog().setTitle(getString(R.string.stock_list));
        return view;
    }
    private class EquipmentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return baseActivity.getStockManager().getStocks("newStocks").getStocks().get("commissioned").size();
        }

        @Override
        public Stock getItem(int i) {
            return baseActivity.getStockManager().getStocks("newStocks").getStocks().get("commissioned").get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getActivity().getLayoutInflater().inflate(R.layout.dialog_list_view,null);
            }
            String stockNumber = getItem(i).getPartNumber();
            ((TextView)view.findViewById(R.id.list_text)).setText(stockNumber);
            return view;
        }
    }
    private class EquipmentDetailAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return baseActivity.getStockManager().getStocks("newStocks").getStocks().get("commissioned").size();
        }

        @Override
        public Stock getItem(int i) {
            return baseActivity.getStockManager().getStocks("newStocks").getStocks().get("commissioned").get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getActivity().getLayoutInflater().inflate(R.layout.detail_view,null);
            }
            ((TextView)view.findViewById(R.id.txt_part_number)).setText(getItem(i).getPartNumber());
            ((TextView)view.findViewById(R.id.txt_description)).setText(getItem(i).getDescription());
            ((TextView)view.findViewById(R.id.txt_quantity)).setText(String.valueOf(getItem(i).getQuantity()));
            ((TextView)view.findViewById(R.id.txt_amount)).setText(getItem(i).getAmount());
            return view;
        }
    }
}
