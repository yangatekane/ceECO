package com.htm.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.htm.BaseActivity;
import com.htm.HomeActivity;
import com.htm.R;
import com.htm.dto.Jobs;
import com.htm.dto.Parts;
import com.htm.dto.Repairs;

import java.util.List;

/**
 * Created by yanga on 2013/10/03.
 */
public class ReportsFragment extends DialogFragment {
    private int totalRequisitions = 0;
    private int totalRequisitionsOpened = 0;
    private int totalRequisitionsClosed = 0;
    private int totalJobs = 0;
    private int totalInhouse = 0;
    private int totalOutsource = 0;
    private int totalJobExpense = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog);
        for (List<Repairs.Repair> list:getHome().getRepairsRequisitionManager().getRequisitions("newRepairs").getRepairs().values()){
            for (Repairs.Repair repair:list){
                totalRequisitions++;
                if (repair.getJob_status().equalsIgnoreCase("opened")){
                    totalRequisitionsOpened++;
                }else {
                    totalRequisitionsClosed++;
                }
            }
        }
        for (Jobs.Job job:getHome().getStockManager().getJobs("newJobs").getJobs().get("commissioned")){
            totalJobs++;
            if (job.getStatus().equalsIgnoreCase("Inhouse")){
                totalInhouse++;
            }else {
                totalOutsource++;
            }
        }
        for (int i=0;i<getHome().getStockManager().getJobs("newJobs").getJobs().get("commissioned").size();i++){
            Parts stock = getHome().getStockManager().getJobs("newJobs").getJobs().get("commissioned").get(i).getParts();

            for (Parts.Part part:stock.getParts()){
                   totalJobExpense=totalJobExpense+Integer.valueOf(part.getAmount());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.single_report,container,false);
        getDialog().setTitle("Monthly Reports");
        ((TextView)view.findViewById(R.id.txt_amount)).setText(String.valueOf(totalRequisitions));
        ((TextView)view.findViewById(R.id.txt_amount_opened)).setText(String.valueOf(totalRequisitionsOpened));
        ((TextView)view.findViewById(R.id.txt_amount_closed)).setText(String.valueOf(totalRequisitionsClosed));
        ((TextView)view.findViewById(R.id.txt_amount_jobs)).setText(String.valueOf(totalJobs));
        ((TextView)view.findViewById(R.id.txt_amount_inhouse)).setText(String.valueOf(totalInhouse));
        ((TextView)view.findViewById(R.id.txt_amount_outsourced)).setText(String.valueOf(totalOutsource));
        ((TextView)view.findViewById(R.id.txt_amount_expense)).setText("R "+String.valueOf(totalJobExpense));
        return view;
    }

    private HomeActivity getHome(){
        return ((HomeActivity)getActivity());
    }

}
