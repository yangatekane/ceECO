package com.htm.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.htm.ExistingEquipment;
import com.htm.R;
import com.htm.dto.Service;

/**
 * Created by yanga on 2013/09/29.
 */
public class ServiceFragment extends DialogFragment {
    private static final String TAG = ServiceFragment.class.getName();
    private View view;
    private Spinner day;
    private Spinner month;
    private Spinner year;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.service_fragment, container,false);
        getDialog().setTitle("Service Date Update");
        setUpDate();
        ((Button) view.findViewById(R.id.btn_update_service)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serviceDate = String.valueOf(day.getSelectedItem()) + " " + String.valueOf(month.getSelectedItem()) + " " + String.valueOf(year.getSelectedItem());
                getExistingEquipment().getServiceManager().saveService(new Service("",getArguments().getString("model"),getArguments().getString("make"),serviceDate,getArguments().getString("serialNumber")));
                getDialog().dismiss();
            }
        });
        return view;
    }
    private void setUpDate(){
        day = (Spinner) view.findViewById(R.id.spin_service_day);
        month = (Spinner) view.findViewById(R.id.spin_service_month);
        year = (Spinner) view.findViewById(R.id.spin_service_year);
    }
    private ExistingEquipment getExistingEquipment(){
        return ((ExistingEquipment)getActivity());
    }
}
