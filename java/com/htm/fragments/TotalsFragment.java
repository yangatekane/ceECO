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

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.htm.BaseActivity;
import com.htm.R;
import com.htm.dto.Repairs;
import com.htm.dto.RepairsTotal;
import com.htm.dto.Stocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yanga on 2013/09/14.
 */
public class TotalsFragment extends DialogFragment {
    final static String TAG = ExportFragment.class.getName();
    private View view;
    private ListView listView;
    private ListView detialList;
    private BaseActivity baseActivity;
    private EquipmentDetailAdapter equipmentDetailAdapter;
    ArrayList<RepairsTotal> totals = new ArrayList<RepairsTotal>();
    private HashMap<String,Integer> makeByQuantity = new HashMap<String, Integer>();
    private ArrayList<Repairs.Repair> repairsByMonth = new ArrayList<Repairs.Repair>();
    public TotalsFragment(Activity activity){
        baseActivity = (BaseActivity) activity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog);
        for (Repairs.Repair repair:baseActivity.getRepairsRequisitionManager().getRequisitions("newRepairs").getRepairs().get("commissioned")){
            if (repair.getDate().split(" ")[1].equalsIgnoreCase(getArguments().getString("month"))){
                repairsByMonth.add(repair);
            }
        }
        for (Repairs.Repair repair:repairsByMonth){
            if (makeByQuantity.containsKey(repair.getMake())){
                int anotherQuantity = makeByQuantity.get(repair.getMake())+1;
                makeByQuantity.put(repair.getMake(),anotherQuantity);
            }else{
                int quantity=1;
                makeByQuantity.put(repair.getMake(),quantity);
            }

        }
        Iterator itr = makeByQuantity.keySet().iterator();
        while (itr.hasNext()){
            RepairsTotal total = new RepairsTotal();
            String make = (String) itr.next();
            total.setCatagory(make);
            total.setQuantity(String.valueOf(makeByQuantity.get(make)));
            totals.add(total);
        }
        equipmentDetailAdapter = new EquipmentDetailAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.file_view, container,false);
        View header = inflater.inflate(R.layout.totals_hear,null);
        detialList = (ListView) view.findViewById(R.id.detail_list);
        detialList.addHeaderView(header);
        detialList.setAdapter(equipmentDetailAdapter);
        getDialog().setTitle(getString(R.string.stats_by_make));
        return view;
    }
    private class EquipmentDetailAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return totals.size();
        }

        @Override
        public RepairsTotal getItem(int i) {
            return totals.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getActivity().getLayoutInflater().inflate(R.layout.stats_view,null);
            }
            //((TextView)view.findViewById(R.id.txt_part_number)).setText(getItem(i).getPartNumber());
            ((TextView)view.findViewById(R.id.txt_description)).setText(getItem(i).getCatagory());
            ((TextView)view.findViewById(R.id.txt_quantity)).setText(getItem(i).getQuantity());
            //((TextView)view.findViewById(R.id.txt_amount)).setText(getItem(i).getAmount());
            return view;
        }
    }
}
