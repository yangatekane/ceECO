package com.htm.biz;

import android.util.Log;

import com.htm.CeECOApplication;
import com.htm.dto.Repair;
import com.htm.dto.Repairs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by yanga on 2013/08/17.
 */
public class RepairsRequisitionManager extends Manager {
    private static final String fileName = "repairs";
    private static final String TAG = RepairsRequisitionManager.class.getName();

    public RepairsRequisitionManager(CeECOApplication application) {
        super(application);
    }
    public void saveRequisitions( String stockCategory,String repairType,
                                  String serialNo,String make,String model,
                                  int requisitionNumber,
                                  String tel, String date, String section,
                                  String department,String floor,String description,
                                  String reportedBy,String receivedBy) throws IOException, InstantiationException, IllegalAccessException {
        Repairs s = getRequisitions(stockCategory);
        if (!s.getRepairs().containsKey(repairType)){
            s.getRepairs().put(repairType, new ArrayList<Repair>());
        }
        s.getRepairs().get(repairType).add(new Repair(serialNo,make,model,requisitionNumber,tel,date,section,
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
        Repairs repairs=new Repairs();
        File objectsFile = getFile(stockCategory,fileName);
        if (!objectsFile.exists()){
            repairs = new Repairs();
        }else {
            try {
                FileInputStream fileIn = new FileInputStream(objectsFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                repairs = (Repairs)in.readObject();
                in.close();
                fileIn.close();

            } catch (ClassNotFoundException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
        if (repairs.getRepairs()==null){
            repairs.setRepairs(new TreeMap<String, List<Repair>>());
        }
        return repairs;
    }
    public void clearCache(String stockCategory){
        clearCache(stockCategory);
    }
}
