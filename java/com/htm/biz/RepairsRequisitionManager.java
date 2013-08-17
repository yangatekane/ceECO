package com.htm.biz;

import android.util.Log;

import com.htm.CeECOApplication;
import com.htm.dto.Repair;
import com.htm.dto.Repairs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by yanga on 2013/08/17.
 */
public class RepairsRequisitionManager extends Manager {
    private static final String fileName = "repairs";
    private static final String TAG = RepairsRequisitionManager.class.getName();

    public RepairsRequisitionManager(CeECOApplication application) {
        super(application);
    }
    public void saveRequisitions( String stockCategory,String repairType, int requisitionNumber, String tel, String date, String section,
                                  String department,String floor,String description,
                                  String reportedBy,String receivedBy) throws IOException, InstantiationException, IllegalAccessException {
        Repairs s = (Repairs) getItem(stockCategory, fileName, Repairs.class);
        if (!s.getRepairs().containsKey(repairType)){
            s.getRepairs().put(repairType, new ArrayList<Repair>());
        }
        s.getRepairs().get(repairType).add(new Repair(requisitionNumber,tel,date,section,
                                               department,floor,description,reportedBy,receivedBy));
        File stockFile = getFile(stockCategory, fileName);
        if (!stockFile.exists()){
            stockFile.createNewFile();
        }
        FileOutputStream fileOut = new FileOutputStream(stockFile);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(s);
        out.close();
        fileOut.close();

    }
    public Repairs getRequisitions(String stockCategory){
        try {
            return (Repairs) getItem(stockCategory, fileName, Repairs.class);
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.getMessage(), e);
        } catch (InstantiationException e) {
            Log.e(TAG,e.getMessage(),e);
        }
        return null;
    }
}
